package controllers;

import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(main.render("Your new application is ready."));
    }
  
    

}
