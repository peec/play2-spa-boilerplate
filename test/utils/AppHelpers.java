package utils;

import static play.test.Helpers.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.codehaus.jackson.node.ObjectNode;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

import play.mvc.HandlerRef;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;

public class AppHelpers {

	
	static public FakeApplication fakeApp(){
		return fakeApplication(conf());
	}

	static public Result jsonReq(ObjectNode o, HandlerRef r){
		FakeRequest fr = fakeRequest();
		fr.withJsonBody(o);
		return callAction(r, fr);
	}

	static public Map<String, String> conf() {
		Map<String, String> configuration = new HashMap<String, String>();
		
		// SMTP
		configuration.put("smtp.host", "localhost");
		configuration.put("smtp.port", ServerSetupTest.SMTP.getPort()+"");
		configuration.put("smtp.ssl", "no");
		configuration.put("smtp.user", "");
		configuration.put("smtp.password", "");
		
		return configuration;
	}
	
	static public GreenMail greenMailer() {
		GreenMail mailer = new GreenMail(ServerSetupTest.SMTP);
		return mailer;
	}
	
}
