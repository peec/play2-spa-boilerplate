package models.auth;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

import com.avaje.ebean.Expr;

import java.util.ArrayList;
import java.util.List;
import utils.*;

@Entity
public class EmailChangeRequest extends Model{
	@Id
	private Long id;

	@JsonIgnore
	private String secretCode;
	
	private String email;
	
	private DateTime createdAt, validTo;
	
	public static final Finder<Long, EmailChangeRequest> find = new Finder<Long, EmailChangeRequest>(Long.class, EmailChangeRequest.class);

	
	
	public EmailChangeRequest(String email, DateTime validTo){
		this.setSecretCode(utils.RandomGenerator.nextSessionId(25));
		this.setEmail(email);
		this.createdAt = DateTime.now();
		this.validTo = validTo;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getCreatedAt(){
		return createdAt;
	}
	public DateTime getValidTo(){
		return validTo;
	}
	
	
	public String getSecretCode() {
		return secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	
	static public AuthorisedUser findByUserIdAndSecretCode(Long userId, String code){
		return 
				AuthorisedUser.find.where()
				.add(Expr.eq("id", userId))
				.add(Expr.eq("emailChange.secretCode", code))
				.add(Expr.gt("emailChange.validTo", DateTime.now()))
				.findUnique();
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	

}