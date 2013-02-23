
import models.auth.*;
import play.Application;
import play.GlobalSettings;
import security.MyRoles;

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
			AuthorisedUser user = new AuthorisedUser("admin@admin.com","admin");
			user.setActivated(true);
			user.roles.add(SecurityRole.findByName("admin"));
			user.save();
		}
	}
}