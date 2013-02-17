package controllers;

import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(main.render("Your new application is ready."));
    }
  
    
    public static Result routes(){
    	return ok(Routes.javascriptRouter("jsRoutes",
    			controllers.api.routes.javascript.AuthService.login(),
    			controllers.api.routes.javascript.AuthService.logout()
    			)).as("text/javascript");
    }
    
    
    public static Result test(){
    	return TODO;
    }
    
}
