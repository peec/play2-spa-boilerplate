package actions;

import controllers.api.AuthService;
import models.auth.*;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;

/**
 * http://stackoverflow.com/questions/9629250/how-to-avoid-passing-parameters-everywhere-in-play2
 * 
 * @author petterkj
 *
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

	
	public static AuthorisedUser current() {
		return (AuthorisedUser)Http.Context.current().args.get("currentUser");
	}
	

	
}
