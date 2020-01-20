
lazy val common_dep = Seq(
    // For testing
    "junit" % "junit" % "4.10" % "test",
    "org.scalatest" %% "scalatest" % "3.0.8" % "test",
    "org.scalacheck" %% "scalacheck" % "1.14.2" % "test")


lazy val TinyInterpreter = project
  .in(file("."))
  .settings(
    name := "TinyInterpreter",
    version := "0.1",
    scalaVersion := "2.13.1",
    libraryDependencies ++= common_dep
  )