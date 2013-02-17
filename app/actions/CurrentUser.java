package actions;

import controllers.api.AuthService;
import models.auth.*;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;

/**
 * Action to get current user.
 * At every subsequent request to a Controller method using "With" annotation on this action:
 * 1. Get the session key of the Request.
 * 2. Query database for the user, add it to the context arguments.
 * 3. Use current() method to get the current user.
 * 
 * 
 * This is action composition: 
 * http://stackoverflow.com/questions/9629250/how-to-avoid-passing-parameters-everywhere-in-play2
 * 
 * @author Petter Kjelkenes <kjelkenes@gmail.com>
 */
public class CurrentUser extends Action.Simple{
	
	@Override
	public Result call(Context ctx) throws Throwable {
		AuthorisedUser user = null;	
		
		String key = AuthService.getSecurityToken(ctx);
		if (key != null){
			try {
				user = UserSession.getUserByKey(key);
			}catch (SessionExpiredException e){
				ctx.session().remove("auth_token"); // Session is expired... remove the key also.
			}
		}
		ctx.args.put("currentUser", user);
		return delegate.call(ctx);
	}

	/**
	 * Returns the current user.
	 * @return The current user or NULL
	 */
	public static AuthorisedUser current() {
		return (AuthorisedUser)Http.Context.current().args.get("currentUser");
	}
	

	
}
