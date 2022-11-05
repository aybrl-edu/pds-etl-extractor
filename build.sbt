ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "spark-proc-job"
  )


libraryDependencies ++= Seq(
  // scala-csv
  "com.github.tototoshi" %% "scala-csv" % "1.3.10",
  // spark
  "org.apache.spark" %% "spark-core" % "3.2.2",
  "org.apache.spark" %% "spark-sql" % "3.2.2",
  "org.apache.spark" %% "spark-hive" % "3.2.2"
)