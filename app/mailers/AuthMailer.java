package mailers;

import play.mvc.Http;
import play.mvc.Http.Request;

import com.typesafe.plugin.MailerAPI;

import controllers.routes;

import models.auth.AuthorisedUser;
import views.txt.mailers.auth.*;
public class AuthMailer extends AMailer{

	static public void sendEmailConfirmation(String activateLink, AuthorisedUser user){
		
		MailerAPI mail = newEmail();
		
		mail.setSubject("User activation");
		mail.addRecipient(user.getEmail());
		
		
		
		mail.send(sendEmailConfirmation.render(user, activateLink).body());
		
	}
	
}
