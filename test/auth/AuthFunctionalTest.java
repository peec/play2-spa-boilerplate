package auth;

import java.util.Map;

import javax.mail.MessagingException;

import models.auth.AuthorisedUser;
import models.auth.ForgotPasswordRequest;
import models.auth.UserSession;

import org.codehaus.jackson.node.ObjectNode;
import org.fluentlenium.core.Fluent;
import org.junit.*;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;

import play.Logger;
import play.Play;
import play.mvc.*;
import play.test.*;
import play.libs.Json;
import play.libs.WS;
import play.libs.F.*;
import plugins.S3Plugin;
import utils.AppHelpers;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class AuthFunctionalTest {

	FakeApplication app;
	GreenMail greenMail;
	@Before
	public void startApp() throws Exception {
		Map<String,String> conf = AppHelpers.conf();
		conf.put("require_email_activation", "true");

		app = fakeApplication(conf);
		Helpers.start(app);

		greenMail = AppHelpers.greenMailer();
		greenMail.start();

	}
	
	@After
	public void stopApp() throws Exception {
		greenMail.stop();
		Helpers.stop(app);
	}

	

	@Test
	public void callIndexHomePage() {
		Result result = callAction(controllers.routes.ref.Application.index());
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("text/html");
		assertThat(charset(result)).isEqualTo("utf-8");
	}


	@Test
	public void loginAndLogoutUser() {

		// Login
		ObjectNode o = Json.newObject();
		o.put("email", "admin@admin.com");
		o.put("password", "admin");
		Result result = AppHelpers.jsonReq(o, controllers.api.routes.ref.AuthService.login());

		assertThat(status(result))
			.isEqualTo(OK);

		String token = session(result).get("auth_token");

		assertThat(token)
			.isNotEmpty();


		// Logout with the session token included.
		result = callAction(controllers.api.routes.ref.AuthService.logout(), fakeRequest().withSession("auth_token", token));
		
		assertThat(status(result))
			.isEqualTo(OK);

	}

	@Test
	public void forgotPasswordSubmittion() throws InterruptedException, MessagingException{

		ObjectNode o = Json.newObject();
		o.put("email", "admin@admin.com");
		
		Result result = AppHelpers.jsonReq(o, controllers.api.routes.ref.AuthService.sendForgotPasswordRequest());
		assertThat(status(result))
			.isEqualTo(OK);

		// Should have gotten one mail.
		assertThat(greenMail.waitForIncomingEmail(5000, 1))
			.isTrue();
		
		assertThat(greenMail.getReceivedMessages()[0].getSubject())
			.isEqualTo("Forgotten password request");
		
		
		AuthorisedUser user = AuthorisedUser.findByEmail("admin@admin.com");
		
		

		// IF access code is wrong, bad request should be thrown.
		// Post a password success change.
		o = Json.newObject();
		o.put("password", "myNewPassword");
		result = AppHelpers.jsonReq(o, 
				controllers.api.routes.ref.AuthService.forgotPassword(
						user.getId(), "this_is_wrong"
						)
				);
		assertThat(status(result))
			.isEqualTo(BAD_REQUEST);

		
		// Post a password success change.
		assertThat(user.getPasswordResets().size())
			.isEqualTo(1);
				
		ForgotPasswordRequest fpr = user.getPasswordResets().get(0);
				
		o = Json.newObject();
		o.put("password", "myNewPassword");
		result = AppHelpers.jsonReq(o, 
				controllers.api.routes.ref.AuthService.forgotPassword(
						user.getId(), fpr.getAccessCode()
				)
		);
		assertThat(status(result))
			.isEqualTo(OK);
		


		
	}


	
	

	@Test
	public void registerUser() throws InterruptedException, MessagingException{


		// Register Success.
		ObjectNode o = Json.newObject();
		o.put("email", "test@test.com");
		o.put("password", "123456");
		o.put("passwordConfirm", "123456");
		Result result = AppHelpers.jsonReq(o, controllers.api.routes.ref.AuthService.register());
		assertThat(status(result))
			.isEqualTo(OK);

		// Should have gotten one mail.
		assertThat(greenMail.waitForIncomingEmail(5000, 1))
			.isTrue();
		assertThat(greenMail.getReceivedMessages()[0].getSubject())
			.isEqualTo("User activation");
	
		
		
		// Username must not be empty.
		o = Json.newObject();
		o.put("email", "");
		o.put("password", "123456");
		o.put("passwordConfirm", "123456");
		result = AppHelpers.jsonReq(o, controllers.api.routes.ref.AuthService.register());
		assertThat(status(result))
			.isEqualTo(BAD_REQUEST);
		assertThat(contentAsString(result))
			.contains("Email is empty");


		// Username was just registered and not longer available.
		o = Json.newObject();
		o.put("email", "test@test.com");
		o.put("password", "123456");
		o.put("passwordConfirm", "123456");
		result = AppHelpers.jsonReq(o, controllers.api.routes.ref.AuthService.register());
		assertThat(status(result))
			.isEqualTo(BAD_REQUEST);
		assertThat(contentAsString(result))
			.contains("Email is already in use");


		// Password confirmation failed.
		o = Json.newObject();
		o.put("email", "test2@test.com");
		o.put("password", "Not the same pw.");
		o.put("passwordConfirm", "123456");
		result = AppHelpers.jsonReq(o, controllers.api.routes.ref.AuthService.register());
		assertThat(status(result))
			.isEqualTo(BAD_REQUEST);
		assertThat(contentAsString(result))
			.contains("Password confirmation is incorrect");



	}






}
