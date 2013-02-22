import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "sample-spa-app"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaEbean,
    // For security annotations.
    "be.objectify" %% "deadbolt-java" % "2.1-SNAPSHOT",
    // Production db driver
    "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
    // For file storage (Amazon S3)
    "com.amazonaws" % "aws-java-sdk" % "1.3.11",
    // Apache Commons for simplified UI utils.
    "commons-io" % "commons-io" % "2.3",
    // For sending emails
    "com.typesafe" %% "play-plugins-mailer" % "2.1.0"
  )
  def customLessEntryPoints(base: File): PathFinder = (
    (base / "app" / "assets" / "stylesheets" / "bootstrap" * "responsive.less") +++ 
    (base / "app" / "assets" / "stylesheets" * "main.less")
  )
  

  val main = play.Project(appName, appVersion, appDependencies).settings(
    requireJs += "main.js",
    requireJsShim := "main.js",
    // requireJsFolder := "javascripts",
    lessEntryPoints <<= baseDirectory(customLessEntryPoints),
    resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("Objectify Play Repository - snapshots", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns)
 
  )

}
