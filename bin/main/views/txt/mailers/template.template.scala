
package views.txt.mailers

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
object template extends BaseScalaTemplate[play.api.templates.Txt,Format[play.api.templates.Txt]](play.api.templates.TxtFormat) with play.api.templates.Template1[Txt,play.api.templates.Txt] {

    /**/
    def apply/*1.2*/()(content: Txt):play.api.templates.Txt = {
        _display_ {

Seq[Any](format.raw/*1.18*/("""

"""),_display_(Seq[Any](/*3.2*/content)),format.raw/*3.9*/("""
__________________

My Company
http://mywebsite.com
(+00) 000-000-000
company@company.com
"""))}
    }
    
    def render(content:Txt): play.api.templates.Txt = apply()(content)
    
    def f:(() => (Txt) => play.api.templates.Txt) = () => (content) => apply()(content)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Feb 22 00:28:16 CET 2013
                    SOURCE: /home/peec/workspace/secretclique/app/views/mailers/template.scala.txt
                    HASH: 43433296922630ad14a9a0b6520efa89a8ba7768
                    MATRIX: 725->1|817->17|854->20|881->27
                    LINES: 26->1|29->1|31->3|31->3
                    -- GENERATED --
                */
            