import org.clulab.sbt.Resolvers

name := "dlnlp-book"
description := "PyTorch examples translated into ScalaPy"
maintainer := "clulab.org"

// Last checked 2021-08-23
val scala11 = "2.11.12" // up to 2.11.12
val scala12 = "2.12.15" // up to 2.12.15
val scala13 = "2.13.8"  // up to 2.13.8

// scala13 is skipped here.
ThisBuild / crossScalaVersions := Seq(scala12, scala11)
ThisBuild / scalaVersion := crossScalaVersions.value.head

resolvers ++= Seq(
//  Resolvers.localResolver,  // Reserve for Two Six.
  Resolvers.clulabResolver, // glove, processors-models
//  Resolvers.jitpackResolver // Ontologies
)

libraryDependencies ++= {
  Seq(
  )
}

lazy val Apps = project
