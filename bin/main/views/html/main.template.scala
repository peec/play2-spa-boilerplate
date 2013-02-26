
package views.html

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
import views.html._
/**/
object main extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(title: String):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.17*/("""
<!DOCTYPE html>
<html>
<head>
	<title>"""),_display_(Seq[Any](/*5.10*/title)),format.raw/*5.15*/("""</title>
	<link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*6.52*/routes/*6.58*/.Assets.at("images/favicon.png"))),format.raw/*6.90*/("""" />
	<link rel="stylesheet" media="all" href=""""),_display_(Seq[Any](/*7.44*/routes/*7.50*/.Assets.at("stylesheets/main.min.css"))),format.raw/*7.88*/("""" />
</head>
<body>
	<div id="appStub"></div>
	"""),_display_(Seq[Any](/*11.3*/helper/*11.9*/.requireJs(core =routes.Assets.at("javascripts/vendor/require.js").url, module = routes.Assets.at("javascripts/main").url))),format.raw/*11.131*/("""
</body>
</html>"""))}
    }
    
    def render(title:String): play.api.templates.Html = apply(title)
    
    def f:((String) => play.api.templates.Html) = (title) => apply(title)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Feb 07 22:52:59 CET 2013
                    SOURCE: /home/peec/workspace/secretclique/app/views/main.scala.html
                    HASH: 978eeca8c8fcf02afbc5f44040d81cd229c05efb
                    MATRIX: 722->1|814->16|889->56|915->61|1010->121|1024->127|1077->159|1160->207|1174->213|1233->251|1316->299|1330->305|1475->427
                    LINES: 26->1|29->1|33->5|33->5|34->6|34->6|34->6|35->7|35->7|35->7|39->11|39->11|39->11
                    -- GENERATED --
                */
            