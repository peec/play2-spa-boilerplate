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
		AuthorisedUser user = new AuthorisedUser("admin@admin.com","pw");
		
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
					AuthorisedUser.createUser("admin2@admin.com", "password");
				} catch (ExistingUserException e) {}
				
				assertThat(AuthorisedUser.authenticate("admin2@admin.com", "password", "127.0.0.1"))
					.isNotNull();
				
				assertThat(AuthorisedUser.authenticate("admin2@admin.com", "pass", "127.0.0.1"))
					.isNull();
				
				AuthorisedUser user = AuthorisedUser.findByEmail("admin2@admin.com");
				assertThat(user)
					.isNotNull();
				assertThat(user.isActivated())
					.isFalse();
				assertThat(user.getEmail())
					.isEqualTo("admin2@admin.com");
				
				
			}
		});
		
	}
	
	
	
	
	
}
