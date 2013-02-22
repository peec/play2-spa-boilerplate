package controllers.api;

import org.codehaus.jackson.node.ObjectNode;

import controllers.API;
import play.Configuration;
import play.libs.Json;
import play.mvc.Result;       
import play.mvc.With;
import utils.JsonResp;
import actions.*;

@With(CurrentUser.class)
public class ConfigService extends API{

	
	/**
	 * Public configuration, clients might need to know if server features are enabled.
	 * @return
	 */
	static public Result load(){

		ObjectNode o = Json.newObject();
		
		// Sections of configuration.
		o.put("auth", auth());
	
		o.put("user", Json.toJson(CurrentUser.current()));
		
		return ok(JsonResp.result(o));
	}
	
	static private ObjectNode auth () {
		ObjectNode o = Json.newObject();
		o.put("require_email_activation", conf().getBoolean("authentication.require_email_activation"));
		return o;
	}
	
	
	static private Configuration conf(){
		return play.Play.application().configuration();
	}
}
