
package views.txt.mailers.auth

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.txt._
/**/
object sendEmailConfirmation extends BaseScalaTemplate[play.api.templates.Txt,Format[play.api.templates.Txt]](play.api.templates.TxtFormat) with play.api.templates.Template3[models.auth.AuthorisedUser,String,models.auth.UserConfirmationRequest,play.api.templates.Txt] {

    /**/
    def apply/*1.2*/(user: models.auth.AuthorisedUser, baseUrl: String, ucr: models.auth.UserConfirmationRequest):play.api.templates.Txt = {
        _display_ {

Seq[Any](format.raw/*1.95*/("""
"""),_display_(Seq[Any](/*2.2*/mailers/*2.9*/.template()/*2.20*/ {_display_(Seq[Any](format.raw/*2.22*/("""

We have gotten a request to register account on our website.

Please click the following link to active your account:

"""),_display_(Seq[Any](/*8.2*/baseUrl)),format.raw/*8.9*/("""#/user/confirm/"""),_display_(Seq[Any](/*8.25*/user/*8.29*/.getId())),format.raw/*8.37*/("""/"""),_display_(Seq[Any](/*8.39*/ucr/*8.42*/.getActivationCode())),format.raw/*8.62*/("""

If this email is unknown to you - ignore this email.

Thanks for registering at our site.

""")))})))}
    }
    
    def render(user:models.auth.AuthorisedUser,baseUrl:String,ucr:models.auth.UserConfirmationRequest): play.api.templates.Txt = apply(user,baseUrl,ucr)
    
    def f:((models.auth.AuthorisedUser,String,models.auth.UserConfirmationRequest) => play.api.templates.Txt) = (user,baseUrl,ucr) => apply(user,baseUrl,ucr)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sat Feb 23 02:17:26 CET 2013
                    SOURCE: /home/peec/workspace/secretclique/app/views/mailers/auth/sendEmailConfirmation.scala.txt
                    HASH: 5b3b337013f996524fc80bce187b2e5c4a207491
                    MATRIX: 809->1|978->94|1014->96|1028->103|1047->114|1086->116|1242->238|1269->245|1320->261|1332->265|1361->273|1398->275|1409->278|1450->298
                    LINES: 26->1|29->1|30->2|30->2|30->2|30->2|36->8|36->8|36->8|36->8|36->8|36->8|36->8|36->8
                    -- GENERATED --
                */
            