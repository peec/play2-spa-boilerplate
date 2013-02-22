package mailers;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

public abstract class AMailer {


	static public MailerAPI newEmail(){
		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
		String from = play.Play.application().configuration().getString("smtp.default.from");
		mail.addFrom(from);
		return mail;
	}
	
	
}
