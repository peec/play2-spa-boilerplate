package controllers.api;

import java.util.concurrent.Callable;

import actions.CurrentUser;
import play.Logger;
import play.mvc.BodyParser;
import play.mvc.Result;       
import play.mvc.With;
import play.mvc.Http.Context;
import play.libs.F.Promise;
import play.libs.F;
import play.libs.Json;
import utils.JsonResp;
import org.codehaus.jackson.JsonNode;           
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;

import com.avaje.ebean.Expr;

import controllers.API;
import controllers.routes;
import mailers.AuthMailer;
import models.auth.*;
import be.objectify.deadbolt.java.actions.*;

@With(CurrentUser.class)
public class AuthService extends API{

	
	/**
	 * Takes JSON to login.
	 * email
	 * password
	 * @return
	 */

	@SubjectNotPresent
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	static public Result login() {
		JsonNode body = jsonBody();
		
		UserSession u = AuthorisedUser.authenticate(body.get("email").asText(), body.get("password").asText(), request().remoteAddress());
		if (u != null){
			
			if(!u.user.isActivated()){
				ObjectNode node = Json.newObject();
				node.put("accessCodes", Json.toJson(u.user.getConfirmationRequests()));
				node.put("uid", u.user.getId());
				
				u.delete(); // Delete the session.
				return badRequest(JsonResp.error(node, "Account not confirmed yet."));
			}
			
			
			ctx().session().put("auth_token", u.loginSecret);
			
			return ok(JsonResp.result(getUserResponse(u.user, u.loginSecret), "Logged in."));
		}else{
			return badRequest(JsonResp.error("Email or password is incorrect."));
		}
	}

	@SubjectPresent
	static public Result logout(){
		
		try {
			UserSession us = UserSession.getUserSessionByKey(getSecurityToken(ctx()));
			if (us != null){
				ctx().session().remove("auth_token");
				us.delete();
				return ok(JsonResp.success("Successfully logged out."));
			}else{
				return badRequest(JsonResp.error("Can not find user."));
			}
		} catch (SessionExpiredException e) {
			return ok(JsonResp.success("Session is expired."));
		}
	}
	
	
	
	/**
	 * 
	 * Creates a user account based on JSON input.
	 * email: the email of the account
	 * password: the password
	 * passwordConfirm: must be equal to password
	 * 
	 */
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	@SubjectNotPresent
	static public Result register() {
		JsonNode body = jsonBody();
		
		String
			email = body.get("email").asText(),
			password = body.get("password").asText(),
			passwordConfirm = body.get("passwordConfirm").asText();
		
		if (email == null || email.isEmpty()){
			return badRequest(JsonResp.error("Email is empty."));
		}
		if (password == null || password.isEmpty() || !password.equals(passwordConfirm)){
			return badRequest(JsonResp.error("Password confirmation is incorrect."));
		}
		
		try {
			final AuthorisedUser user = AuthorisedUser.createUser(email, passwordConfirm);
			

			Promise<Boolean> result = sendConfirmationEmail(user);
			
			return async(result.map(new F.Function<Boolean, Result> () {
				@Override
				public Result apply(Boolean emailSent){
					ObjectNode node = JsonResp.result(Json.toJson(user), "Account created.");
					node.put("emailSent", emailSent);
					return ok(node);
				}
			}));
			
		} catch (ExistingUserException e) {
			return badRequest(JsonResp.error("Email is already in use."));
		}
	}
	static private Promise<Boolean> sendConfirmationEmail(final AuthorisedUser user){
		final boolean activationEnabled = play.Play.application().configuration().getBoolean("authentication.require_email_activation");
		final String baseUrl = routes.Application.index().absoluteURL(request());
		
		return play.libs.Akka.future(
				new Callable<Boolean>() {
					public Boolean call() {
						if (activationEnabled){
							UserConfirmationRequest ucr = new UserConfirmationRequest();
							user.getConfirmationRequests().add(ucr);
							user.save();
							AuthMailer.sendEmailConfirmation(baseUrl, user, ucr);
							return true;
						}else {
							user.setActivated(true);
							user.save();
							return false;
						}
					}
				}
		);
	}
	
	
	static public Result getUserInfoForConfirmation(Long id, final String accessCode){
		final AuthorisedUser user = UserConfirmationRequest.findByUserIdAndAccessCode(id, accessCode);
		if (user == null){
			return badRequest(JsonResp.error("No access to confirmation."));
		}
		
		return ok(JsonResp.result(Json.toJson(user)));
	}
	
	static public Result sendConfirmationEmail(Long id, final String accessCode){
		final AuthorisedUser user = UserConfirmationRequest.findByUserIdAndAccessCode(id, accessCode);
		
		if (user == null) {
			return badRequest(JsonResp.error("Account already validated."));
		}
		
		
		Promise<Boolean> result = sendConfirmationEmail(user);
		
		return async(result.map(new F.Function<Boolean, Result> () {
			@Override
			public Result apply(Boolean emailSent){
				ObjectNode result = Json.newObject();
				result.put("id", user.getId());
				result.put("accessCode", accessCode);
				result.put("emailSent", emailSent);
				ObjectNode node = JsonResp.result(result, "Registration email sent.");
				return ok(node);
			}
		}));
	}
	
	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	@SubjectNotPresent
	static public Result activateAccount(){
		JsonNode body = jsonBody();
		
		Long userId = body.get("userId").asLong();
		String activationCode = body.get("activationCode").asText();
		
		if (userId == 0 || activationCode == null || activationCode == ""){
			return badRequest(JsonResp.error("Invalid parameters for activation."));
		}
		
		// Find the user.
		AuthorisedUser user = UserConfirmationRequest.canConfirmAccount(userId, activationCode);
		
		if (user == null){
			return badRequest(JsonResp.error("Invalid activation or already acivated."));
		}
		
		user.getConfirmationRequests().clear();
		user.setActivated(true);
		user.save();
		
		return ok(JsonResp.success("Activated user account."));
	}
	
	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	@SubjectPresent
	static public Result editProfile(){
		JsonNode body = jsonBody();
		
		AuthorisedUser user = CurrentUser.current();
		
		
		String inEmail = body.get("email").asText();
		
		if (body.get("password") != null){
			String currentPassword = body.get("currentPassword").asText();
			
			if (!user.checkPassword(currentPassword)){
				return badRequest(JsonResp.error("Current password is invalid."));
			}
			
			String password = body.get("password").asText();
			user.setPassword(password);
		}
		if (!inEmail.equals(user.getEmail())){
			user.setEmailChange(new EmailChangeRequest(inEmail, DateTime.now().plusHours(72)));
			sendEmailChangeMail(user);
		}
		// Regret email change
		if (user.getEmailChange() != null && body.get("emailChange") == null){
			user.setEmailChange(null);
		}
		
		
		
		user.save();
		
		return ok(JsonResp.result(Json.toJson(user)));
	}
	
	static public Result confirmEmailChange (Long userId, String secretCode) {
		AuthorisedUser user = EmailChangeRequest.findByUserIdAndSecretCode(userId, secretCode);
		
		if (user == null){
			return badRequest(JsonResp.error("Confirm request not found or expired"));
		}
		user.setEmail(user.getEmailChange().getEmail());
		user.setEmailChange(null);
		user.save();
		return ok(JsonResp.result(Json.toJson(user), "Email changed to " + user.getEmail()));
	}

	static private Promise<Boolean> sendEmailChangeMail(final AuthorisedUser user){
		final String baseUrl = routes.Application.index().absoluteURL(request());
		
		return play.libs.Akka.future(
				new Callable<Boolean>() {
					public Boolean call() {
						AuthMailer.sendEmailChangeMail(baseUrl, user);
						return true;
					}
				}
		);
	}
	
	
	@SubjectPresent
	static public Result getEditProfile(){
		return ok(JsonResp.result(Json.toJson(CurrentUser.current())));
	}
	
	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	static public Result sendForgotPasswordRequest(){
		JsonNode body = jsonBody();
		
		String email = body.get("email").asText();
		
		AuthorisedUser user = AuthorisedUser.findByEmail(email);
		
		if (user == null) {
			return badRequest(JsonResp.error("Could not find user"));
		}
		ForgotPasswordRequest fpr = new ForgotPasswordRequest();
		user.getPasswordResets().add(fpr);
		
		user.save();
		
		
		Promise<ForgotPasswordRequest> result = sendForgotPasswordEmail(user, fpr);
		
		return async(result.map(new F.Function<ForgotPasswordRequest, Result> () {
			@Override
			public Result apply(ForgotPasswordRequest fpr){
				ObjectNode node = JsonResp.result(Json.toJson(fpr), "Forgot password request sent.");
				return ok(node);
			}
		}));
	}
	
	static private Promise<ForgotPasswordRequest> sendForgotPasswordEmail(final AuthorisedUser user, final ForgotPasswordRequest fpr){
		final String baseUrl = routes.Application.index().absoluteURL(request());
		
		return play.libs.Akka.future(
				new Callable<ForgotPasswordRequest>() {
					public ForgotPasswordRequest call() {
						AuthMailer.sendForgotPassword(baseUrl, user, fpr);
						return fpr;
					}
				}
		);
	}
	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	static public Result forgotPassword(Long userId, String accessCode){
		JsonNode body = jsonBody();

		String password = body.get("password").asText();
		
		AuthorisedUser user = ForgotPasswordRequest.findByUserIdAndAccessCode(userId, accessCode);
		
		if(user == null){
			return badRequest(JsonResp.error("Forgot password request not successful."));
		}
		
		if (password == null || password.isEmpty()){
			return badRequest(JsonResp.error("Password was not sent."));
		}
		user.setPassword(password);
		user.getPasswordResets().clear();
		user.saveManyToManyAssociations("passwordResets");
		user.save();
		
		return ok(JsonResp.result(Json.toJson(user)));
	}
	
	static public Result getForgotPasswordRequest(Long userId, String accessCode){
		AuthorisedUser user = ForgotPasswordRequest.findByUserIdAndAccessCode(userId, accessCode);
		
		if(user == null){
			return badRequest(JsonResp.error("Access code invalid."));
		}
		ObjectNode o = Json.newObject();
		o.put("id", userId);
		o.put("accessCode", accessCode);
		o.put("user", Json.toJson(user));
		
		return ok(JsonResp.result(o));
	}
	
	
	static private ObjectNode getUserResponse(AuthorisedUser user, String token){
		ObjectNode o = Json.newObject();
		o.put("auth_token", token);
		o.put("user", Json.toJson(user));
		return o;
	}

	/**
	 * Helper method that returns the auth token from the cookie or session.
	 * @param ctx The context
	 * @return The auth token or null.
	 */
	public static String getSecurityToken(Context ctx){
		String key = ctx.session().get("auth_token");
		if (key == null)key = ctx.request().queryString().get("auth_token") != null ? ctx.request().queryString().get("auth_token")[0] : null;
		return key;
	}
	
	
}
