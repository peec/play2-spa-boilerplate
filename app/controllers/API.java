package controllers;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import play.mvc.Controller;

public class API extends Controller{

	final static protected ObjectMapper mapper = new ObjectMapper();
	
	static protected JsonNode jsonBody(){
		return request().body().asJson();
	}
	
}
