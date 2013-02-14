package controllers.api;

import actions.CurrentUser;
import play.mvc.BodyParser;
import play.mvc.Result;       
import play.mvc.With;
import play.mvc.Http.Context;
import play.libs.Json;
import utils.JsonResp;
import org.codehaus.jackson.JsonNode;           
import org.codehaus.jackson.node.ObjectNode;
import controllers.API;
import models.auth.*;
import be.objectify.deadbolt.java.actions.*;

@With(CurrentUser.class)
public class AuthService extends API{

	
	/**
	 * Takes JSON to login.
	 * email
	 * password
	 * 
	 * @return
	 */

	@SubjectNotPresent
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	static public Result login() {
		JsonNode body = jsonBody();
		
		UserSession u = AuthorisedUser.authenticate(body.get("username").asText(), body.get("password").asText(), request().remoteAddress());
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
	 * username: the username of the account
	 * password: the password
	 * passwordConfirm: must be equal to password
	 * 
	 */
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	@SubjectNotPresent
	static public Result register() {
		JsonNode body = jsonBody();
		
		String
			username = body.get("username").asText(),
			password = body.get("password").asText(),
			passwordConfirm = body.get("passwordConfirm").asText();
		
		if (username == null || username.isEmpty()){
			return badRequest(JsonResp.error("Username is empty."));
		}
		if (password == null || password.isEmpty() || !password.equals(passwordConfirm)){
			return badRequest(JsonResp.error("Password confirmation is incorrect."));
		}
		
		try {
			AuthorisedUser user = AuthorisedUser.createUser(username, passwordConfirm);
			return ok(JsonResp.result(Json.toJson(user), "Account created."));
		} catch (ExistingUserException e) {
			return badRequest(JsonResp.error("Username is already in use."));
		}
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
