
import org.codehaus.jackson.node.ObjectNode;

import models.auth.*;
import play.Application;
import play.GlobalSettings;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import security.MyRoles;
import utils.JsonResp;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class Global extends GlobalSettings {
	@Override
	public void onStart(Application application) {
		fixtures();
	}

	

	public void fixtures() {

		
		if (SecurityRole.find.findRowCount() == 0) {
			for (MyRoles r : MyRoles.values()) {
				SecurityRole role = new SecurityRole();
				role.name = r.getName();
				role.save();
			}
		}

		if (AuthorisedUser.find.findRowCount() == 0) {
			AuthorisedUser user = new AuthorisedUser("admin@admin.com","admin");
			user.setActivated(true);
			user.roles.add(SecurityRole.findByName("admin"));
			user.save();
		}
	}
	

	@Override
	public Result onError(RequestHeader request, Throwable t) {
		return Controller.internalServerError(JsonResp.error(t.getMessage()));
	}
	
	
}