// cooler sbt prompt with helpful information
// addSbtPlugin("com.scalapenos" % "sbt-prompt" % "0.2.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.3.0") // "latest.release"
//addSbtPlugin("com.typesafe.sbt" % "sbt-webdriver" % "latest.release")
//addSbtPlugin("com.typesafe.sbt" % "sbt-js-engine" % "latest.release")
// twirl templates integration (https://github.com/playframework/twirl)
addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.1.1")
// for generating an eclipse importable project
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "4.0.0")
// for working in develoment with automatic restart of the web server
addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")
// for creating a package ― option A (see docs)
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")
// for creating a package ― option B (see docs)
// addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.0-RC1")
// for creating a package ― option C (see docs)
// addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.7.9")

// sometimes useful plugins
// addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.5.0") // get build time information into the runtime
// addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.5")   // build for javascript runtime
