import com.avaje.ebean.Ebean;
import models.auth.*;
import play.Application;
import play.GlobalSettings;
import security.MyRoles;

import java.util.ArrayList;
import java.util.Arrays;

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

//		if (UserPermission.find.findRowCount() == 0) {
//			UserPermission permission = new UserPermission();
//			permission.value = "printers.edit";
//			
//			permission.save();
//		}

		if (AuthorisedUser.find.findRowCount() == 0) {
			AuthorisedUser user = new AuthorisedUser("admin","%23icDJ1jx9812jxasEJwj12nxhuScrub");
			user.roles.add(SecurityRole.findByName("admin"));
			user.save();
		}
	}
}