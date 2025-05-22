
ThisBuild / scalaVersion := DependencyVersions.scala

lazy val root = project
  .in(file("."))
  .settings(
    name := "busmingle-backend",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= Dependencies.zio.value,
    libraryDependencies ++= Dependencies.quill.value,
    libraryDependencies ++= Dependencies.jwt.value,
    libraryDependencies ++= Dependencies.tapir.value,
    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )
