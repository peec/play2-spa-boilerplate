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

	private boolean activated;
	
	@ManyToMany(cascade=CascadeType.ALL)	
	public List<SecurityRole> roles = new ArrayList<SecurityRole>();

	@ManyToMany(cascade=CascadeType.ALL)
	public List<UserPermission> permissions = new ArrayList<UserPermission>();


	@OneToMany(cascade=CascadeType.ALL)
	private List<UserConfirmationRequest> confirmationRequests = new ArrayList<UserConfirmationRequest>();

	@OneToMany(cascade=CascadeType.ALL)
	private List<ForgotPasswordRequest> passwordResets = new ArrayList<ForgotPasswordRequest>();

	@OneToOne(cascade=CascadeType.ALL)
	private EmailChangeRequest emailChange;




	public static final Finder<Long, AuthorisedUser> find = new Finder<Long, AuthorisedUser>(Long.class, AuthorisedUser.class);

	
	public AuthorisedUser(){}
	
	public AuthorisedUser(String email, String password){
		setEmail(email);
		setPassword(password);
	}

	public List<ForgotPasswordRequest> getPasswordResets() {
		return passwordResets;
	}

	public EmailChangeRequest getEmailChange() {
		return emailChange;
	}

	public void setEmailChange(EmailChangeRequest emailChange) {
		this.emailChange = emailChange;
	}

	public void setPasswordResets(List<ForgotPasswordRequest> passwordResets) {
		this.passwordResets = passwordResets;
	}	
	public List<UserConfirmationRequest> getConfirmationRequests() {
		return confirmationRequests;
	}

	public void setConfirmationRequests(
			List<UserConfirmationRequest> confirmationRequests) {
		this.confirmationRequests = confirmationRequests;
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


	public boolean isActivated() {
		return activated;
	}

	@JsonIgnore
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	
	
	
	public static AuthorisedUser findByEmail(String email) {
		return find.where().eq("email", email).findUnique();
	}

	public boolean checkPassword(String password){
		return BCrypt.checkpw(password, this.password);
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
		
		if (u.checkPassword(password)){
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