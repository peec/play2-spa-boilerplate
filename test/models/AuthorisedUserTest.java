package models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.auth.AuthorisedUser;
import models.auth.ExistingUserException;

import org.codehaus.jackson.JsonNode;
import org.junit.*;
import play.test.*;
import utils.BCrypt;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


public class AuthorisedUserTest {

	@Test
	public void passwordMatchesPassword () {
		AuthorisedUser user = new AuthorisedUser("username","pw");
		
		assertThat(BCrypt.checkpw("pw", user.getPassword()))
			.isEqualTo(true);
		
		assertThat(user.getPassword())
			.isNotEqualTo("pw");
	}
	
	@Test
	public void testCreateAndAuthenticateUser () {
		

		running(fakeApplication(inMemoryDatabase()), new Runnable(){
			public void run() {
				try {
					AuthorisedUser.createUser("username", "password");
				} catch (ExistingUserException e) {}
				
				assertThat(AuthorisedUser.authenticate("username", "password", "127.0.0.1"))
					.isNotNull();
				
				assertThat(AuthorisedUser.authenticate("username", "pass", "127.0.0.1"))
					.isNull();
				
				AuthorisedUser user = AuthorisedUser.findByUserName("username");
				assertThat(user)
					.isNotNull();
				assertThat(user.getUserName())
					.isEqualTo("username");
				
				
				
				
			}
		});
		
	}
	
	
	
	
	
}
