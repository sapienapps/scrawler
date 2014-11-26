name := "crawler4j"

version := "3.5.7"

organization := "com.sapienapps"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.11" % Test,
  "org.specs2" % "specs2_2.11" % "2.4" % Test,
  "log4j" % "log4j" % "1.2.17",
  "org.apache.httpcomponents" % "httpclient" % "4.3.5",
  "com.sleepycat" % "je" % "5.0.73",
  "org.apache.tika" % "tika-parsers" % "1.6" excludeAll(
    ExclusionRule(organization = "org.apache.poi"),
    ExclusionRule(organization = "edu.ucar"),
    ExclusionRule(organization = "jdom"),
    ExclusionRule(organization = "rome"),
    ExclusionRule(organization = "org.bouncycastle")
    )
)

publishTo := Some(Resolver.file("crawler4j", new File(Path.userHome + "/Dropbox/public/libs")))