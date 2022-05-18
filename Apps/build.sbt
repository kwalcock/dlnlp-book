name := "apps"
description := ""

resolvers ++= Seq(
//  Resolvers.localResolver, // Reserve for Two Six.
//  Resolvers.clulabResolver // processors-models, transitive dependency
)

libraryDependencies ++= {
  Seq(
    "me.shadaj" %% "scalapy-core" % "0.5.2" // ,
//    "me.shadaj" %% "scalapy-numpy" % "0.1.0" // +6-14ca0424"
  )
}

fork := true

assembly / mainClass := Some("org.clulab.dlnlp_book.apps.LogisticRegressionApp")
