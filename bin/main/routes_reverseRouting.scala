// @SOURCE:/home/peec/workspace/secretclique/conf/routes
// @HASH:86883767896fd4a999994e96c2fcf339df5e9786
// @DATE:Mon Feb 25 22:17:36 CET 2013

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:31
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:23
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:13
package controllers.api {

// @LINE:13
class ReverseConfigService {
    

// @LINE:13
def load(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "api/config")
}
                                                
    
}
                          

// @LINE:31
class ReverseSignedAmazonS3Handler {
    

// @LINE:31
def sign(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "api/s3/sign")
}
                                                
    
}
                          

// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:23
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
class ReverseAuthService {
    

// @LINE:24
def sendConfirmationEmail(id:Long, accessCode:String): Call = {
   Call("PUT", _prefix + { _defaultPrefix } + "api/user/send-confirm/" + implicitly[PathBindable[Long]].unbind("id", id) + "/" + implicitly[PathBindable[String]].unbind("accessCode", accessCode))
}
                                                

// @LINE:22
def activateAccount(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "api/user/activate")
}
                                                

// @LINE:18
def login(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "api/auth")
}
                                                

// @LINE:19
def logout(): Call = {
   Call("DELETE", _prefix + { _defaultPrefix } + "api/auth")
}
                                                

// @LINE:21
def register(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "api/user/create")
}
                                                

// @LINE:27
def getForgotPasswordRequest(id:Long, accessCode:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "api/user/forgotpassword/" + implicitly[PathBindable[Long]].unbind("id", id) + "/" + implicitly[PathBindable[String]].unbind("accessCode", accessCode))
}
                                                

// @LINE:20
def getUserInfo(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "api/auth")
}
                                                

// @LINE:23
def editProfile(): Call = {
   Call("PUT", _prefix + { _defaultPrefix } + "api/user/profile")
}
                                                

// @LINE:26
def forgotPassword(id:Long, accessCode:String): Call = {
   Call("PUT", _prefix + { _defaultPrefix } + "api/user/forgotpassword/" + implicitly[PathBindable[Long]].unbind("id", id) + "/" + implicitly[PathBindable[String]].unbind("accessCode", accessCode))
}
                                                

// @LINE:25
def sendForgotPasswordRequest(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "api/user/forgotpassword")
}
                                                
    
}
                          
}
                  

// @LINE:9
// @LINE:6
package controllers {

// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index(): Call = {
   Call("GET", _prefix)
}
                                                
    
}
                          

// @LINE:9
class ReverseAssets {
    

// @LINE:9
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          
}
                  


// @LINE:31
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:23
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:13
package controllers.api.javascript {

// @LINE:13
class ReverseConfigService {
    

// @LINE:13
def load : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.ConfigService.load",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/config"})
      }
   """
)
                        
    
}
              

// @LINE:31
class ReverseSignedAmazonS3Handler {
    

// @LINE:31
def sign : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.SignedAmazonS3Handler.sign",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "api/s3/sign"})
      }
   """
)
                        
    
}
              

// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:23
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
class ReverseAuthService {
    

// @LINE:24
def sendConfirmationEmail : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.AuthService.sendConfirmationEmail",
   """
      function(id,accessCode) {
      return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "api/user/send-confirm/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("accessCode", accessCode)})
      }
   """
)
                        

// @LINE:22
def activateAccount : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.AuthService.activateAccount",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "api/user/activate"})
      }
   """
)
                        

// @LINE:18
def login : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.AuthService.login",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "api/auth"})
      }
   """
)
                        

// @LINE:19
def logout : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.AuthService.logout",
   """
      function() {
      return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "api/auth"})
      }
   """
)
                        

// @LINE:21
def register : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.AuthService.register",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "api/user/create"})
      }
   """
)
                        

// @LINE:27
def getForgotPasswordRequest : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.AuthService.getForgotPasswordRequest",
   """
      function(id,accessCode) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/user/forgotpassword/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("accessCode", accessCode)})
      }
   """
)
                        

// @LINE:20
def getUserInfo : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.AuthService.getUserInfo",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/auth"})
      }
   """
)
                        

// @LINE:23
def editProfile : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.AuthService.editProfile",
   """
      function() {
      return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "api/user/profile"})
      }
   """
)
                        

// @LINE:26
def forgotPassword : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.AuthService.forgotPassword",
   """
      function(id,accessCode) {
      return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "api/user/forgotpassword/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("accessCode", accessCode)})
      }
   """
)
                        

// @LINE:25
def sendForgotPasswordRequest : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.AuthService.sendForgotPasswordRequest",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "api/user/forgotpassword"})
      }
   """
)
                        
    
}
              
}
        

// @LINE:9
// @LINE:6
package controllers.javascript {

// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        
    
}
              

// @LINE:9
class ReverseAssets {
    

// @LINE:9
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              
}
        


// @LINE:31
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:23
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:13
package controllers.api.ref {

// @LINE:13
class ReverseConfigService {
    

// @LINE:13
def load(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.ConfigService.load(), HandlerDef(this, "controllers.api.ConfigService", "load", Seq(), "GET", """ Configuration""", _prefix + """api/config""")
)
                      
    
}
                          

// @LINE:31
class ReverseSignedAmazonS3Handler {
    

// @LINE:31
def sign(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.SignedAmazonS3Handler.sign(), HandlerDef(this, "controllers.api.SignedAmazonS3Handler", "sign", Seq(), "POST", """""", _prefix + """api/s3/sign""")
)
                      
    
}
                          

// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:23
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
class ReverseAuthService {
    

// @LINE:24
def sendConfirmationEmail(id:Long, accessCode:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.AuthService.sendConfirmationEmail(id, accessCode), HandlerDef(this, "controllers.api.AuthService", "sendConfirmationEmail", Seq(classOf[Long], classOf[String]), "PUT", """""", _prefix + """api/user/send-confirm/$id<[^/]+>/$accessCode<[^/]+>""")
)
                      

// @LINE:22
def activateAccount(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.AuthService.activateAccount(), HandlerDef(this, "controllers.api.AuthService", "activateAccount", Seq(), "POST", """""", _prefix + """api/user/activate""")
)
                      

// @LINE:18
def login(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.AuthService.login(), HandlerDef(this, "controllers.api.AuthService", "login", Seq(), "POST", """""", _prefix + """api/auth""")
)
                      

// @LINE:19
def logout(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.AuthService.logout(), HandlerDef(this, "controllers.api.AuthService", "logout", Seq(), "DELETE", """""", _prefix + """api/auth""")
)
                      

// @LINE:21
def register(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.AuthService.register(), HandlerDef(this, "controllers.api.AuthService", "register", Seq(), "POST", """""", _prefix + """api/user/create""")
)
                      

// @LINE:27
def getForgotPasswordRequest(id:Long, accessCode:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.AuthService.getForgotPasswordRequest(id, accessCode), HandlerDef(this, "controllers.api.AuthService", "getForgotPasswordRequest", Seq(classOf[Long], classOf[String]), "GET", """""", _prefix + """api/user/forgotpassword/$id<[^/]+>/$accessCode<[^/]+>""")
)
                      

// @LINE:20
def getUserInfo(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.AuthService.getUserInfo(), HandlerDef(this, "controllers.api.AuthService", "getUserInfo", Seq(), "GET", """""", _prefix + """api/auth""")
)
                      

// @LINE:23
def editProfile(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.AuthService.editProfile(), HandlerDef(this, "controllers.api.AuthService", "editProfile", Seq(), "PUT", """""", _prefix + """api/user/profile""")
)
                      

// @LINE:26
def forgotPassword(id:Long, accessCode:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.AuthService.forgotPassword(id, accessCode), HandlerDef(this, "controllers.api.AuthService", "forgotPassword", Seq(classOf[Long], classOf[String]), "PUT", """""", _prefix + """api/user/forgotpassword/$id<[^/]+>/$accessCode<[^/]+>""")
)
                      

// @LINE:25
def sendForgotPasswordRequest(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.AuthService.sendForgotPasswordRequest(), HandlerDef(this, "controllers.api.AuthService", "sendForgotPasswordRequest", Seq(), "POST", """""", _prefix + """api/user/forgotpassword""")
)
                      
    
}
                          
}
                  

// @LINE:9
// @LINE:6
package controllers.ref {

// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      
    
}
                          

// @LINE:9
class ReverseAssets {
    

// @LINE:9
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          
}
                  
      