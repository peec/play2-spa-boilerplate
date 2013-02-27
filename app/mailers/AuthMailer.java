package mailers;

import play.mvc.Http;
import play.mvc.Http.Request;

import com.typesafe.plugin.MailerAPI;

import controllers.routes;

import models.auth.AuthorisedUser;
import models.auth.EmailChangeRequest;
import models.auth.ForgotPasswordRequest;
import models.auth.UserConfirmationRequest;
import views.txt.mailers.auth.*;

public class AuthMailer extends AMailer{

	static public void sendEmailConfirmation(String activateLink, AuthorisedUser user, UserConfirmationRequest ucr){
		
		MailerAPI mail = newEmail();
		
		mail.setSubject("User activation");
		mail.addRecipient(user.getEmail());
		
		
		
		mail.send(sendEmailConfirmation.render(user, activateLink, ucr).body());
		
	}
	
	static public void  sendForgotPassword(String baseUrl, AuthorisedUser user, ForgotPasswordRequest fpr){
		
		MailerAPI mail = newEmail();
		
		mail.setSubject("Forgotten password request");
		mail.addRecipient(user.getEmail());
		
		mail.send(sendForgotPassword.render(user, baseUrl, fpr).body());
		
	}
	
	static public void sendEmailChangeMail(String baseUrl, AuthorisedUser user){
		MailerAPI mail = newEmail();
		
		mail.setSubject("Forgotten password request");
		mail.addRecipient(user.getEmailChange().getEmail());
		
		EmailChangeRequest req = user.getEmailChange();
		
		
		mail.send(sendEmailChange.render(baseUrl, user, req).body());
	}
}
