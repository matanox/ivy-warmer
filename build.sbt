import play.twirl.sbt.SbtTwirl
import play.twirl.sbt.Import.TwirlKeys
import com.typesafe.sbt.web.SbtWeb.autoImport._
import WebKeys._

/*
 * Import for https://github.com/agemooij/sbt-prompt, providing an informative multi-project sbt prompt.
 * You will need to follow the instructions there for setting up the fancy unicode symbols which it
 * uses for display. In particular you'll also need to configure your terminal preferences to use
 * one of the fonts from https://github.com/powerline/fonts.
 *
 * import com.scalapenos.sbt.prompt.SbtPrompt.autoImport._
 */

lazy val launcher = "server.Launch"

lazy val commonSettings = Seq(
  //promptTheme := Scalapenos,
  version := "0.1-SNAPSHOT",
  organization := "none",
  scalaVersion := "2.11.7",
  /* unit tests */
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.4.3" % "test",
  testFrameworks += new TestFramework("utest.runner.Framework"),

  libraryDependencies ++= Seq(
    "org.scala-lang.modules" %% "scala-pickling" % "0.10.1",
    "org.scala-lang" % "scala-compiler" % scalaVersion.value // for using scala.tools.nsc.io.File
  )
)

lazy val fetcher = (project in file("."))
  .enablePlugins(SbtTwirl)
  .enablePlugins(SbtWeb)
  .settings(commonSettings)
  .settings(
    /* packaging for deploy with sbt-assembly */
    mainClass in assembly := Some(launcher), // main class in sbt-assembly created uberjar
    assemblyMergeStrategy in assembly := {   // avoids horrible slf4j related class path conflicts when packaging
      case PathList("org", "apache", "commons", "logging", xs @ _*) => MergeStrategy.first
      case PathList("org", "slf4j", "impl", xs @ _*) => MergeStrategy.first
      case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    },

    /* twirl template engine integration (https://github.com/matanster/play-as-library/blob/661e729d9c820b653ea00f155771d1ebccabd8ef/build.sbt) */
    sourceDirectories in (Compile, TwirlKeys.compileTemplates) := (unmanagedSourceDirectories in Compile).value,
    TwirlKeys.templateImports += "play.api.templates.PlayMagic._",
    TwirlKeys.templateImports += "views.html.play20",

    /* sbt-revolver plugin settings */
    mainClass in reStart := Some(launcher), // so you can use ~re-start to run the web server while enjoying auto-restart on code change
    reColors := Seq("magenta"),

    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided",
      "com.typesafe.play" %% "play-json" % "2.4.6", // "2.6.0-SNAPSHOT"
      //"org.scala-lang.modules" %% "scala-pickling" % "0.10.1",
      //"com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.6.0-1",
      "io.circe" %% "circe-core" % "0.2.1",
      "io.circe" %% "circe-generic" % "0.2.1",
      "io.circe" %% "circe-parse" % "0.2.1",
      "com.lihaoyi" %% "pprint" % "0.3.6",
      /* json */
      "com.typesafe.play" %% "play-json" % "2.4.6",
      /* nicer scala files IO */
      "com.github.pathikrit" %% "better-files" % "2.14.0",
      /* statistics functions */
      "org.apache.commons" % "commons-math3" % "3.5",
      /* safely escaped csv writing */
      "com.github.tototoshi" %% "scala-csv" % "1.3.0"
    ),
    /* scalalike jdbc */
    libraryDependencies ++= Seq(
      "org.scalikejdbc" %% "scalikejdbc"       % "2.4.1",  //"2.3.5",
      "com.h2database"  %  "h2"                % "1.4.191",
      "ch.qos.logback"  %  "logback-classic"   % "1.1.3"
    ),
    libraryDependencies ++= Seq("com.sksamuel.elastic4s" %% "elastic4s-core" % "2.3.0"),
    /* slick */
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.1.1",
      //"org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-codegen" % "3.1.1",
      "mysql" % "mysql-connector-java" % "5.1.38",
      "com.zaxxer" % "HikariCP-java6" % "2.3.9"
    ),
    /* auxiliary algorithms */
    libraryDependencies ++= Seq(
      /* aho-corasick implementation */
      "org.ahocorasick" % "ahocorasick" % "0.3.0",
      /* akka */
      //"com.typesafe.akka"   %%  "akka-testkit"  % "2.3.9" % "test",
      "com.typesafe.akka" %% "akka-actor" % "2.3.9",
      /* efficient at-most-n edit distance matching */
      "com.github.universal-automata" % "liblevenshtein" % "3.0.0" // "com.github.dylon" % "liblevenshtein" % "2.2.0"
    ),

    /* play framework as a library */
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % "2.4.6", // "2.6.0-SNAPSHOT"
      "com.typesafe.play" %% "play-netty-server" % "2.4.6" // "2.6.0-SNAPSHOT"
    ),

    /* distance metrics - from my fork */
    resolvers += Resolver.mavenLocal,
    libraryDependencies += "info.debatty" % "java-string-similarity" % "0.13",

    fork in run := true, // for more objective monitoring with oracle VisualVM when run as web server
    publishArtifact := false)

/*
 * makes ctrl+c stop the current task rather than quit sbt
 */
cancelable in Global := true

/*
 * for allowing getting prompt input from the user when running mains not the web server
 * (http://www.scala-sbt.org/0.13/docs/Forking.html#Configuring+Input)
 */
connectInput in run := true
