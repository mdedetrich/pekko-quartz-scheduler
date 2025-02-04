import xerial.sbt.Sonatype._
import sbt.sbtpgp.Compat._
name := "pekko-quartz-scheduler"

organization := "io.github.samueleresca"

version := "1.2.0-pekko-1.0.x"

val Scala212Version = "2.12.19"
val Scala213Version = "2.13.13"
val Scala3Version = "3.3.3"
val PekkoVersion = "1.0.2"

ThisBuild / scalaVersion := Scala213Version
ThisBuild / crossScalaVersions := Seq(Scala212Version, Scala213Version, Scala3Version)
ThisBuild / scalacOptions ++= Seq("-language:postfixOps")

libraryDependencies ++= Seq(
  "org.apache.pekko"     %% "pekko-actor"       % PekkoVersion,
  "org.apache.pekko"     %% "pekko-actor-typed" % PekkoVersion,
  ("org.quartz-scheduler" % "quartz"            % "2.3.2")
    .exclude("com.zaxxer", "HikariCP-java7"),
  "org.apache.pekko" %% "pekko-testkit"             % PekkoVersion % Test,
  "org.apache.pekko" %% "pekko-actor-testkit-typed" % PekkoVersion % Test,
  "org.specs2"       %% "specs2-core"               % "4.20.5"     % Test,
  "org.specs2"       %% "specs2-junit"              % "4.20.5"     % Test,
  "junit"             % "junit"                     % "4.13.2"     % Test,
  "org.slf4j"         % "slf4j-api"                 % "2.0.12"     % Test,
  "org.slf4j"         % "slf4j-jcl"                 % "1.7.36"     % Test,
  "org.scalatest"    %% "scalatest"                 % "3.2.18"     % Test
)

// Sonatype release settings
pomIncludeRepository := { _ => false }
sonatypeCredentialHost := "s01.oss.sonatype.org"
publishTo := sonatypePublishToBundle.value
sonatypeProjectHosting := Some(
  GitHubHosting(user = "samueleresca", repository = "pekko-quartz-scheduler", email = "samuele.resca@gmail.com")
)
// Metadata referrsing to licenses, website, and SCM (source code management)
licenses := Seq("APL2" -> url("https://www.apache.org/licenses/LICENSE-2.0.txt"))
sonatypeProfileName := "io.github.samueleresca"
publishMavenStyle := true
scmInfo := Some(
  ScmInfo(
    url("https://github.com/samueleresca/pekko-quartz-scheduler"),
    "scm:git@github.com:samueleresca/pekko-quartz-scheduler.git"
  )
)

ThisBuild / githubWorkflowBuild := Seq(WorkflowStep.Sbt(List("test")))

ThisBuild / githubWorkflowTargetTags ++= Seq("*.*.*")
ThisBuild / githubWorkflowPublishTargetBranches := Seq.empty
ThisBuild / githubWorkflowPublish := Seq.empty

ThisBuild / githubWorkflowOSes := Seq("ubuntu-latest", "macos-latest")

ThisBuild / githubWorkflowJavaVersions := Seq(
  JavaSpec.temurin("8"),
  JavaSpec.temurin("11"),
  JavaSpec.temurin("17"),
  JavaSpec.temurin("21")
)
