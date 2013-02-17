package utils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

public class JsonResp {

	static public ObjectNode success(String msg){
		return stringResponse(msg, true);
	}
	static public ObjectNode error(String msg){
		return stringResponse(msg, false);
	}
	
	static public ObjectNode result(JsonNode o){
		return objectResponse(o);
	}
	static public ObjectNode result(JsonNode o, String message){
		ObjectNode ob = objectResponse(o);
		ob.put("message", message);
		return ob;
	}
	
	
	static protected ObjectNode objectResponse(JsonNode o){
		ObjectNode result = Json.newObject();
		result.put("result", o);
		result.put("success", true);
		return result;
	}
	
	static protected ObjectNode stringResponse(String msg, boolean success){
		ObjectNode result = Json.newObject();
		result.put("message", msg);
		result.put("success", success);
		return result;
	}

}