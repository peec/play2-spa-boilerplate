
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
object sendForgotPassword extends BaseScalaTemplate[play.api.templates.Txt,Format[play.api.templates.Txt]](play.api.templates.TxtFormat) with play.api.templates.Template3[models.auth.AuthorisedUser,String,models.auth.ForgotPasswordRequest,play.api.templates.Txt] {

    /**/
    def apply/*1.2*/(user: models.auth.AuthorisedUser, baseUrl: String, fpr: models.auth.ForgotPasswordRequest):play.api.templates.Txt = {
        _display_ {

Seq[Any](format.raw/*1.93*/("""
"""),_display_(Seq[Any](/*2.2*/mailers/*2.9*/.template()/*2.20*/ {_display_(Seq[Any](format.raw/*2.22*/("""
We have received a request to reset your password.
If you have made this request, please follow the following link to reset your password:

"""),_display_(Seq[Any](/*6.2*/baseUrl)),format.raw/*6.9*/("""#/user/forgotpassword/new/"""),_display_(Seq[Any](/*6.36*/user/*6.40*/.getId())),format.raw/*6.48*/("""/"""),_display_(Seq[Any](/*6.50*/fpr/*6.53*/.getAccessCode())),format.raw/*6.69*/("""

The link will be active for 24 hours.
If you did not ask for a new password - ignore this email.

""")))})))}
    }
    
    def render(user:models.auth.AuthorisedUser,baseUrl:String,fpr:models.auth.ForgotPasswordRequest): play.api.templates.Txt = apply(user,baseUrl,fpr)
    
    def f:((models.auth.AuthorisedUser,String,models.auth.ForgotPasswordRequest) => play.api.templates.Txt) = (user,baseUrl,fpr) => apply(user,baseUrl,fpr)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Feb 25 21:38:45 CET 2013
                    SOURCE: /home/peec/workspace/secretclique/app/views/mailers/auth/sendForgotPassword.scala.txt
                    HASH: d96f134d5443845bf28bfcbd53847d85543bb3d9
                    MATRIX: 804->1|971->92|1007->94|1021->101|1040->112|1079->114|1255->256|1282->263|1344->290|1356->294|1385->302|1422->304|1433->307|1470->323
                    LINES: 26->1|29->1|30->2|30->2|30->2|30->2|34->6|34->6|34->6|34->6|34->6|34->6|34->6|34->6
                    -- GENERATED --
                */
            