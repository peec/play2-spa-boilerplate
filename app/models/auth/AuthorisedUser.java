package models.auth;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;
import play.db.ebean.Model;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import utils.*;


@Entity
public class AuthorisedUser extends Model implements Subject {
	@Id
	private Long id;

	private String email;
	
	private String password;

	@JsonIgnore
	private String activationCode;
	
	
	@ManyToMany(cascade=CascadeType.ALL)	
	public List<SecurityRole> roles = new ArrayList<SecurityRole>();

	@ManyToMany(cascade=CascadeType.ALL)
	public List<UserPermission> permissions = new ArrayList<UserPermission>();

	public static final Finder<Long, AuthorisedUser> find = new Finder<Long, AuthorisedUser>(Long.class, AuthorisedUser.class);

	
	public AuthorisedUser(){}
	
	public AuthorisedUser(String email, String password){
		setEmail(email);
		setPassword(password);
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setPassword(String password){
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@Override
	public List<? extends Role> getRoles() {
		return roles;
	}

	@Override
	public List<? extends Permission> getPermissions() {
		return permissions;
	}

	@JsonIgnore
	@Override
	public String getIdentifier() {
		return email;
	}


	public String getActivationCode() {
		return activationCode;
	}

	
	/**
	 * Inactivates the user and generating activation code.
	 */
	public void generateActivationCode() {
		this.activationCode = utils.RandomGenerator.nextSessionId(20);
	}
	
	/**
	 * Activates this user.
	 */
	public void activate(){
		activationCode = null;
	}
	
	/**
	 * Checks if the current user is activated.
	 * @return
	 */
	public boolean isActive(){
		return activationCode == null;
	}
	
	
	
	public static AuthorisedUser findByEmail(String email) {
		return find.where().eq("email", email).findUnique();
	}


	
	/**
	 * Tries to authenticate a user with email + password.
	 * @param email The email
	 * @param password The password (unencrypted)
	 * @return Returns null on failure, UserAccount object on success.
	 */
	public static AuthorisedUser authenticate(String email, String password){
		// Get user.
		AuthorisedUser u = findByEmail(email);
		if (u == null)return null;
		
		if (BCrypt.checkpw(password, u.password)){
			return u;
		}else{
			return null;
		}
	}
	
	/**
	 * Authenticates a user, if it was authenticated a session is generated and returned.
	 * The session is stored in the database.
	 * @param email Email
	 * @param password Password
	 * @param host The host name of the client.
	 * @return null on failure, UserSession object on success.
	 */
	public static UserSession authenticate(String email, String password, String host) {
		AuthorisedUser u = authenticate(email, password);
		if (u != null){
			UserSession sess = new UserSession(u, host);
			sess.save();
			return sess;
		}
		return null;
	}
	
	
	public static AuthorisedUser createUser(String email, String password) throws ExistingUserException{
		if (findByEmail(email) != null)throw new ExistingUserException("Username already exist.");
		AuthorisedUser user = new AuthorisedUser(email, password);
		user.save();
		return user;
	}

	
	
}