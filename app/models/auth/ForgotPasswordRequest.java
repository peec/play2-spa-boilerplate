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
public class ForgotPasswordRequest extends Model{
	@Id
	private Long id;

	@JsonIgnore
	private String accessCode;
	
	
	private DateTime createdAt, validTo;
	
	public static final Finder<Long, ForgotPasswordRequest> find = new Finder<Long, ForgotPasswordRequest>(Long.class, ForgotPasswordRequest.class);

	
	
	public ForgotPasswordRequest(){
		this.setAccessCode(utils.RandomGenerator.nextSessionId(25));
		this.createdAt = DateTime.now();
		this.validTo = DateTime.now().plusHours(24);
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
				.add(Expr.eq("passwordResets.accessCode", code))
				.add(Expr.gt("passwordResets.validTo", DateTime.now()))
				.findUnique();
	}

	

}
