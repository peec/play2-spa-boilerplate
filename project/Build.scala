import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "secretclique"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaEbean,
    "be.objectify" %% "deadbolt-java" % "2.1-SNAPSHOT",
    "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
  )
  def customLessEntryPoints(base: File): PathFinder = (
    (base / "app" / "assets" / "stylesheets" / "bootstrap" * "responsive.less") +++ 
    (base / "app" / "assets" / "stylesheets" * "main.less")
  )
  

  val main = play.Project(appName, appVersion, appDependencies).settings(
    requireJs += "main.js",
    lessEntryPoints <<= baseDirectory(customLessEntryPoints),
    resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("Objectify Play Repository - snapshots", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns)
 
  )

}
