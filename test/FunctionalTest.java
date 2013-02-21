import models.auth.UserSession;

import org.codehaus.jackson.node.ObjectNode;
import org.fluentlenium.core.Fluent;
import org.junit.*;

import play.Logger;
import play.Play;
import play.mvc.*;
import play.test.*;
import play.libs.Json;
import play.libs.WS;
import play.libs.F.*;
import plugins.S3Plugin;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class FunctionalTest {

	
	
	@Test
	public void callIndexHomePage() {
		Result result = callAction(controllers.routes.ref.Application.index());
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("text/html");
		assertThat(charset(result)).isEqualTo("utf-8");
	}
	
	
	@Test
	public void loginAndLogoutUser() {
		running(fakeApplication(inMemoryDatabase()), new Runnable(){
			public void run() {
				
				// Login
				ObjectNode o = Json.newObject();
				o.put("email", "admin@admin.com");
				o.put("password", "admin");
				Result result = jsonReq(o, controllers.api.routes.ref.AuthService.login());
				
				assertThat(status(result))
					.isEqualTo(OK);
				
				String token = session(result).get("auth_token");
				
				assertThat(token)
					.isNotEmpty();
				
				
				// Logout with the session token included.
				result = callAction(controllers.api.routes.ref.AuthService.logout(), 
						fakeRequest().withSession("auth_token", token));
				assertThat(status(result))
					.isEqualTo(OK);
				
			}
		});
	}
	
	
	@Test
	public void registerUser() {
		running(fakeApplication(inMemoryDatabase()), new Runnable(){
			public void run() {
				// Login Success.
				ObjectNode o = Json.newObject();
				o.put("email", "test@test.com");
				o.put("password", "123456");
				o.put("passwordConfirm", "123456");
				Result result = jsonReq(o, controllers.api.routes.ref.AuthService.register());
				assertThat(status(result))
					.isEqualTo(OK);
				
				// Username must not be empty.
				o = Json.newObject();
				o.put("email", "");
				o.put("password", "123456");
				o.put("passwordConfirm", "123456");
				result = jsonReq(o, controllers.api.routes.ref.AuthService.register());
				assertThat(status(result))
					.isEqualTo(BAD_REQUEST);
				assertThat(contentAsString(result))
					.contains("Email is empty");
				
				
				// Username was just registered and not longer available.
				o = Json.newObject();
				o.put("email", "test@test.com");
				o.put("password", "123456");
				o.put("passwordConfirm", "123456");
				result = jsonReq(o, controllers.api.routes.ref.AuthService.register());
				assertThat(status(result))
					.isEqualTo(BAD_REQUEST);
				assertThat(contentAsString(result))
					.contains("Email is already in use");
				
				
				// Password confirmation failed.
				o = Json.newObject();
				o.put("email", "test2@test.com");
				o.put("password", "Not the same pw.");
				o.put("passwordConfirm", "123456");
				result = jsonReq(o, controllers.api.routes.ref.AuthService.register());
				assertThat(status(result))
					.isEqualTo(BAD_REQUEST);
				assertThat(contentAsString(result))
					.contains("Password confirmation is incorrect");
			
			}
		});
	}
	
	
	
	/*
	@Test
	public void signAmazonS3Upload () {
		running(fakeApplication(inMemoryDatabase()), new Runnable(){
			public void run() {
				
				
				ObjectNode o;
				Result result;
				
				String loginToken = getLoginToken();
				
				o = Json.newObject();
				o.put("fileName", "test.png");
				o.put("contentType", "image/png");
				
				result = callAction(controllers.api.routes.ref.SignedAmazonS3Handler.sign(),
						fakeRequest().withSession("auth_token", loginToken).withJsonBody(o));
				
				assertThat(status(result))
					.isEqualTo(OK);
			}
		});
	}
	*/

	
	private String getLoginToken(){
		ObjectNode o = Json.newObject();
		o.put("username", "admin");
		o.put("password", "admin");
		Result result = jsonReq(o, controllers.api.routes.ref.AuthService.login());
		
		String token = session(result).get("auth_token");
		
		return token;
	}
	
	private Result jsonReq(ObjectNode o, HandlerRef r){
		FakeRequest fr = fakeRequest();
		fr.withJsonBody(o);
		return callAction(r, fr);
	}

}
