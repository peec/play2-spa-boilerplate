package models.auth;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.avaje.ebean.Expr;

import java.util.ArrayList;
import java.util.List;
import utils.*;

@Entity
public class UserConfirmationRequest extends Model{
	@Id
	private Long id;

	private String accessCode;
	
	@JsonIgnore
	private String activationCode;

	
	
	public static final Finder<Long, UserConfirmationRequest> find = new Finder<Long, UserConfirmationRequest>(Long.class, UserConfirmationRequest.class);

	
	
	public UserConfirmationRequest(){
		this.activationCode = utils.RandomGenerator.nextSessionId(20);
		this.setAccessCode(utils.RandomGenerator.nextSessionId(20));
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivationCode() {
		return activationCode;
	}


	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	
	static public AuthorisedUser findByUserIdAndAccessCode(Long userId, String code){
		return 
				AuthorisedUser.find.where()
				.add(Expr.eq("id", userId))
				.add(Expr.eq("confirmationRequests.accessCode", code))
				.findUnique();
	}

	static public AuthorisedUser canConfirmAccount(Long userId, String code){
		return 
				AuthorisedUser.find.where()
				.add(Expr.eq("id", userId))
				.add(Expr.eq("confirmationRequests.activationCode", code))
				.findUnique();
	}

}
