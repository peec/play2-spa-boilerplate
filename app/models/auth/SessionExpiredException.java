package models.auth;

@SuppressWarnings("serial")
public class SessionExpiredException extends Exception{
	public SessionExpiredException(String message){
		super(message);
	}
}
