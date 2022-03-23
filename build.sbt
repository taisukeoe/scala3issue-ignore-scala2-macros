ThisBuild / scalaVersion := "2.13.8"

lazy val scala3Proj =
  project
    .settings(
      scalaVersion := "3.1.1",
      // scalacOptions += "-Xignore-scala2-macros"
    )
    .dependsOn(scala2Proj)

lazy val scala2Proj = project.settings(
  scalaVersion := "2.13.8",
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided"
)
