import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "secretclique"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean
  )
  def customLessEntryPoints(base: File): PathFinder = (
    (base / "app" / "assets" / "stylesheets" / "bootstrap" * "responsive.less") +++ 
    (base / "app" / "assets" / "stylesheets" * "main.less")
  )
  

  val main = play.Project(appName, appVersion, appDependencies).settings(
    requireJs += "main.js",
    lessEntryPoints <<= baseDirectory(customLessEntryPoints)     
  )

}
