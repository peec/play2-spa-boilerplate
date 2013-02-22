package controllers.api;

import java.util.concurrent.Callable;

import actions.CurrentUser;
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
	
	@SubjectPresent
	static public Result getUserInfo(){
		return ok(JsonResp.result(Json.toJson(getUserResponse(CurrentUser.current(), getSecurityToken(ctx())))));
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
			final String baseUrl;			
			final boolean activationEnabled = play.Play.application().configuration().getBoolean("authentication.require_email_activation");
			
			if (activationEnabled) {
				// Set activation code.
				user.generateActivationCode();
				user.save();
			}
			baseUrl = routes.Application.index().absoluteURL(request());

			
			Promise<Boolean> result = play.libs.Akka.future(
					new Callable<Boolean>() {
						public Boolean call() {
							if (activationEnabled){
								AuthMailer.sendEmailConfirmation(baseUrl, user);
								return true;
							}
							return false;
						}
					}
			);
			
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
		AuthorisedUser user = AuthorisedUser
				.find
				.where()
				.add(Expr.eq("id", userId))
				.add(Expr.eq("activationCode", activationCode))
				.add(Expr.isNotNull("activationCode"))
				.findUnique();
		
		if (user == null){
			return badRequest(JsonResp.error("Invalid activation or already acivated."));
		}
		
		user.activate();
		user.save();
		
		return ok(JsonResp.success("Activated user account."));
	}
	
	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	@SubjectPresent
	static public Result editProfile(){
		JsonNode body = jsonBody();
		
		AuthorisedUser user = CurrentUser.current();
		
		String password = body.get("password").asText();
		if (password != null && password.isEmpty()){
			user.setPassword(password);
		}
		
		user.save();
		
		return ok(JsonResp.result(Json.toJson(user)));
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
