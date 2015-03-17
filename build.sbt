name          := """spray-slf-template"""

organization  := "com.meltwater.slf"

version       := "0.1-SNAPSHOT"

homepage      := Some(url("http://www.meltwater.com"))

startYear     := Some(2015)

scalaVersion  := "2.11.6"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-encoding",
  "UTF-8",
  "-Xlint",
  "-Yclosure-elim",
  "-Yinline"
)

assemblyOutputPath in assembly := file(s"assembly/${name.value}_2.11-${version.value}-assembly.jar")

test in assembly := {}

assemblyOption in assembly := (assemblyOption in assembly).value.copy(cacheUnzip = false)

artifact in(Compile, assembly) := {
  val art = (artifact in(Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}

addArtifact(artifact in(Compile, assembly), assembly)

libraryDependencies ++= {
  val akkaVersion         = "2.3.9"
  val sprayVersion        = "1.3.2"
  val scalaTestVersion    = "2.2.1"
  val configVersion       = "1.2.1"
  val spraySwaggerVersion = "0.5.0"
  val json4sVersion       = "3.2.11"
  Seq(
    "com.typesafe.akka"     %% "akka-actor"                % akkaVersion,
    "com.typesafe.akka"     %% "akka-slf4j"                % akkaVersion,
    "io.spray"              %% "spray-routing"             % sprayVersion,
    "io.spray"              %% "spray-client"              % sprayVersion,
    "io.spray"              %% "spray-caching"             % sprayVersion,
    "com.gettyimages"       %% "spray-swagger"             % spraySwaggerVersion excludeAll ExclusionRule(organization = "org.json4s"),
    "org.json4s"            %% "json4s-jackson"            % json4sVersion,
    "com.typesafe"           % "config"                    % configVersion,
    "com.typesafe.akka"     %% "akka-testkit"              % akkaVersion        % Test,
    "io.spray"              %% "spray-testkit"             % sprayVersion       % Test,
    "org.scalatest"         %% "scalatest"                 % scalaTestVersion   % Test
  )
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.typesafeRepo("releases"),
  "spray repo" at "http://repo.spray.io"
)

Revolver.settings

//publishTo := {
//  val meltwaterNexus = "http://maven.meltwater.com/nexus/content/repositories/"
//  if (isSnapshot.value)
//    Some("snapshots" at meltwaterNexus + "Meltwater-Imageservice-Snapshots")
//  else
//    Some("releases" at meltwaterNexus + "Meltwater-Imageservice-Releases")
//}

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

net.virtualvoid.sbt.graph.Plugin.graphSettings

buildInfoSettings

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](name, organization, version, homepage, startYear, scalaVersion, sbtVersion, scalacOptions, javacOptions)

buildInfoPackage := "com.meltwater.slf"
