package models.auth;
import java.util.List;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.TxRunnable;
import com.avaje.ebean.validation.*;

import play.data.format.*;
import play.db.ebean.Model;
import utils.RandomGenerator;


@Entity
public class UserSession extends Model{

	// Expire 12 hours
	static public final int UNTIL_EXPIRE = 3200 * 12; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
	@JsonIgnore
	@ManyToOne
	public AuthorisedUser user;
	
	@NotNull
	@Column(length=1500)
	public String loginSecret;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public DateTime due;
	
	public String host;
	
	
	
	public UserSession(AuthorisedUser user, String host){
		this.user = user;
		this.loginSecret = RandomGenerator.nextSessionId(35);
		updateDue();
		this.host = host;
	}
	
	public UserSession updateDue(){
		due = DateTime.now().plusSeconds(UNTIL_EXPIRE);
		return this;
	}
	
	
	// STATIC METHODS.
	public static final Finder<Long, UserSession> find = new Finder<Long, UserSession>(Long.class, UserSession.class);

	
		
	/**
	 * Gets a user session by key.
	 * @param key The session key ( loginSecret).
	 * @return Returns a valid User object if key is correct. NULL otherwise.
	 * @throws SessionExpiredException If session is expired it throws exception.
	 */
	static public UserSession getUserSessionByKey(String key) throws SessionExpiredException{
		
		UserSession u = find.where().eq("loginSecret", key).findUnique();
		if (u == null)return null;
		// Check if it's due.
		if (u.due.isBeforeNow()){
			throw new SessionExpiredException("Session expiered.");
		}
		
		return u;
	}
	
	/**
	 * Gets a user by key.
	 * @see UserSession.getUserSessionByKey
	 */
	static public AuthorisedUser getUserByKey(String key) throws SessionExpiredException{
		UserSession sess = getUserSessionByKey(key);
		if (sess != null)return sess.user;
		return null;
	}
	
	
	static public int deleteOldSessions(){
		/*
		 * Delete sessions with time due less then NOW.
		 **/
		final List<UserSession> sessions = find.where().lt("due", DateTime.now()).findList();
		
		// Run mass delete.
		Ebean.execute(new TxRunnable() {  
			public void run() {
				for(UserSession s : sessions){
					s.delete();
				}
			}
		});
		
		return sessions.size();
	}
	
	
}
