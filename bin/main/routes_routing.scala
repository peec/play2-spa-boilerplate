// @SOURCE:/home/peec/workspace/secretclique/conf/routes
// @HASH:86883767896fd4a999994e96c2fcf339df5e9786
// @DATE:Mon Feb 25 22:17:36 CET 2013


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix  
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" } 


// @LINE:6
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:9
private[this] lazy val controllers_Assets_at1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+"""))))
        

// @LINE:13
private[this] lazy val controllers_api_ConfigService_load2 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/config"))))
        

// @LINE:18
private[this] lazy val controllers_api_AuthService_login3 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/auth"))))
        

// @LINE:19
private[this] lazy val controllers_api_AuthService_logout4 = Route("DELETE", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/auth"))))
        

// @LINE:20
private[this] lazy val controllers_api_AuthService_getUserInfo5 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/auth"))))
        

// @LINE:21
private[this] lazy val controllers_api_AuthService_register6 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/user/create"))))
        

// @LINE:22
private[this] lazy val controllers_api_AuthService_activateAccount7 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/user/activate"))))
        

// @LINE:23
private[this] lazy val controllers_api_AuthService_editProfile8 = Route("PUT", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/user/profile"))))
        

// @LINE:24
private[this] lazy val controllers_api_AuthService_sendConfirmationEmail9 = Route("PUT", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/user/send-confirm/"),DynamicPart("id", """[^/]+"""),StaticPart("/"),DynamicPart("accessCode", """[^/]+"""))))
        

// @LINE:25
private[this] lazy val controllers_api_AuthService_sendForgotPasswordRequest10 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/user/forgotpassword"))))
        

// @LINE:26
private[this] lazy val controllers_api_AuthService_forgotPassword11 = Route("PUT", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/user/forgotpassword/"),DynamicPart("id", """[^/]+"""),StaticPart("/"),DynamicPart("accessCode", """[^/]+"""))))
        

// @LINE:27
private[this] lazy val controllers_api_AuthService_getForgotPasswordRequest12 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/user/forgotpassword/"),DynamicPart("id", """[^/]+"""),StaticPart("/"),DynamicPart("accessCode", """[^/]+"""))))
        

// @LINE:31
private[this] lazy val controllers_api_SignedAmazonS3Handler_sign13 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/s3/sign"))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/config""","""controllers.api.ConfigService.load()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/auth""","""controllers.api.AuthService.login()"""),("""DELETE""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/auth""","""controllers.api.AuthService.logout()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/auth""","""controllers.api.AuthService.getUserInfo()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/user/create""","""controllers.api.AuthService.register()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/user/activate""","""controllers.api.AuthService.activateAccount()"""),("""PUT""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/user/profile""","""controllers.api.AuthService.editProfile()"""),("""PUT""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/user/send-confirm/$id<[^/]+>/$accessCode<[^/]+>""","""controllers.api.AuthService.sendConfirmationEmail(id:Long, accessCode:String)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/user/forgotpassword""","""controllers.api.AuthService.sendForgotPasswordRequest()"""),("""PUT""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/user/forgotpassword/$id<[^/]+>/$accessCode<[^/]+>""","""controllers.api.AuthService.forgotPassword(id:Long, accessCode:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/user/forgotpassword/$id<[^/]+>/$accessCode<[^/]+>""","""controllers.api.AuthService.getForgotPasswordRequest(id:Long, accessCode:String)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/s3/sign""","""controllers.api.SignedAmazonS3Handler.sign()""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
       
    
def routes:PartialFunction[RequestHeader,Handler] = {        

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
   }
}
        

// @LINE:9
case controllers_Assets_at1(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        

// @LINE:13
case controllers_api_ConfigService_load2(params) => {
   call { 
        invokeHandler(controllers.api.ConfigService.load(), HandlerDef(this, "controllers.api.ConfigService", "load", Nil,"GET", """ Configuration""", Routes.prefix + """api/config"""))
   }
}
        

// @LINE:18
case controllers_api_AuthService_login3(params) => {
   call { 
        invokeHandler(controllers.api.AuthService.login(), HandlerDef(this, "controllers.api.AuthService", "login", Nil,"POST", """""", Routes.prefix + """api/auth"""))
   }
}
        

// @LINE:19
case controllers_api_AuthService_logout4(params) => {
   call { 
        invokeHandler(controllers.api.AuthService.logout(), HandlerDef(this, "controllers.api.AuthService", "logout", Nil,"DELETE", """""", Routes.prefix + """api/auth"""))
   }
}
        

// @LINE:20
case controllers_api_AuthService_getUserInfo5(params) => {
   call { 
        invokeHandler(controllers.api.AuthService.getUserInfo(), HandlerDef(this, "controllers.api.AuthService", "getUserInfo", Nil,"GET", """""", Routes.prefix + """api/auth"""))
   }
}
        

// @LINE:21
case controllers_api_AuthService_register6(params) => {
   call { 
        invokeHandler(controllers.api.AuthService.register(), HandlerDef(this, "controllers.api.AuthService", "register", Nil,"POST", """""", Routes.prefix + """api/user/create"""))
   }
}
        

// @LINE:22
case controllers_api_AuthService_activateAccount7(params) => {
   call { 
        invokeHandler(controllers.api.AuthService.activateAccount(), HandlerDef(this, "controllers.api.AuthService", "activateAccount", Nil,"POST", """""", Routes.prefix + """api/user/activate"""))
   }
}
        

// @LINE:23
case controllers_api_AuthService_editProfile8(params) => {
   call { 
        invokeHandler(controllers.api.AuthService.editProfile(), HandlerDef(this, "controllers.api.AuthService", "editProfile", Nil,"PUT", """""", Routes.prefix + """api/user/profile"""))
   }
}
        

// @LINE:24
case controllers_api_AuthService_sendConfirmationEmail9(params) => {
   call(params.fromPath[Long]("id", None), params.fromPath[String]("accessCode", None)) { (id, accessCode) =>
        invokeHandler(controllers.api.AuthService.sendConfirmationEmail(id, accessCode), HandlerDef(this, "controllers.api.AuthService", "sendConfirmationEmail", Seq(classOf[Long], classOf[String]),"PUT", """""", Routes.prefix + """api/user/send-confirm/$id<[^/]+>/$accessCode<[^/]+>"""))
   }
}
        

// @LINE:25
case controllers_api_AuthService_sendForgotPasswordRequest10(params) => {
   call { 
        invokeHandler(controllers.api.AuthService.sendForgotPasswordRequest(), HandlerDef(this, "controllers.api.AuthService", "sendForgotPasswordRequest", Nil,"POST", """""", Routes.prefix + """api/user/forgotpassword"""))
   }
}
        

// @LINE:26
case controllers_api_AuthService_forgotPassword11(params) => {
   call(params.fromPath[Long]("id", None), params.fromPath[String]("accessCode", None)) { (id, accessCode) =>
        invokeHandler(controllers.api.AuthService.forgotPassword(id, accessCode), HandlerDef(this, "controllers.api.AuthService", "forgotPassword", Seq(classOf[Long], classOf[String]),"PUT", """""", Routes.prefix + """api/user/forgotpassword/$id<[^/]+>/$accessCode<[^/]+>"""))
   }
}
        

// @LINE:27
case controllers_api_AuthService_getForgotPasswordRequest12(params) => {
   call(params.fromPath[Long]("id", None), params.fromPath[String]("accessCode", None)) { (id, accessCode) =>
        invokeHandler(controllers.api.AuthService.getForgotPasswordRequest(id, accessCode), HandlerDef(this, "controllers.api.AuthService", "getForgotPasswordRequest", Seq(classOf[Long], classOf[String]),"GET", """""", Routes.prefix + """api/user/forgotpassword/$id<[^/]+>/$accessCode<[^/]+>"""))
   }
}
        

// @LINE:31
case controllers_api_SignedAmazonS3Handler_sign13(params) => {
   call { 
        invokeHandler(controllers.api.SignedAmazonS3Handler.sign(), HandlerDef(this, "controllers.api.SignedAmazonS3Handler", "sign", Nil,"POST", """""", Routes.prefix + """api/s3/sign"""))
   }
}
        
}
    
}
        