package security;

import controllers.api.AuthService;
import akka.event.slf4j.Logger;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.core.models.Subject;
import models.auth.AuthorisedUser;
import models.auth.SessionExpiredException;
import models.auth.UserSession;
import play.mvc.Http;
import play.mvc.Result;

import utils.JsonResp;
import views.html.accessFailed;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class MyDeadboltHandler extends AbstractDeadboltHandler {
	public Result beforeAuthCheck(Http.Context context) {
		// returning null means that everything is OK. Return a real result if
		// you want a redirect to a login page or
		// somewhere else
		return null;
	}

	public Subject getSubject(Http.Context context) {
		String sessionKey = AuthService.getSecurityToken(context);
		if (sessionKey == null || sessionKey == "")return null;
		try {
			return UserSession.getUserByKey(sessionKey);
		} catch (SessionExpiredException e) {
			return null;
		}
	}

	public DynamicResourceHandler getDynamicResourceHandler(Http.Context context) {
		return new MyDynamicResourceHandler();
	}

	@Override
	public Result onAuthFailure(Http.Context context, String content) {
		return forbidden(JsonResp.error("Access denied"));
	}
	
}
