package controllers.api;

import java.util.ArrayList;
import java.util.List;

import actions.CurrentUser;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;       
import play.mvc.With;
import play.mvc.Http.Context;
import play.data.Form;
import play.data.validation.Constraints.*;
import play.libs.Json;
import play.libs.Json.*;                        
import utils.JsonResp;
import static play.libs.Json.toJson;
import org.codehaus.jackson.JsonNode;           
import org.codehaus.jackson.node.ObjectNode;
import com.avaje.ebean.ValidationException;
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
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	@SubjectNotPresent
	static public Result login() {
		JsonNode body = jsonBody();
		
		UserSession u = AuthorisedUser.authenticate(body.get("username").asText(), body.get("password").asText(), request().remoteAddress());
		if (u != null){
			ctx().session().put("auth_token", u.loginSecret);
			
			ObjectNode o = Json.newObject();
			o.put("auth_token", u.loginSecret);
			o.put("user", Json.toJson(u.user));
			return ok(JsonResp.result(o, "Logged in."));
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
		return ok(JsonResp.result(Json.toJson(CurrentUser.current())));
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
