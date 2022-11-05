package sparkjobs.proto

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object LoadFromHDFS {

  val hdfsHost: String = "hdfs://192.168.1.2:9000"
  val hdfsPath : String = s"$hdfsHost/bronze/titanic.csv"
  val hivePath : String = s"$hdfsHost/sql/metadata/hive"
  val sparkHost: String = "local"

  def loadFromHDFS: Unit = {

    // log something
    println(s"${"!" * 25} START ${"!" * 25}")

    // Init spark session
    val sparkSession = SparkSession
      .builder
      .master(sparkHost)
      .appName("PDS Data Processing")
      .enableHiveSupport()
      .getOrCreate()

    // Load data from HDFS
    val df = sparkSession.read
      .format("csv")
      .option("header", "true")
      .option("mode", "DROPMALFORMED")
      .load(hdfsPath)

    // PoC, show dataframe
    df.show(10)

    // Save data to hive
    df.createOrReplaceTempView("titanicTable")
    sparkSession.sql("create table titanic_table as select * from titanicTable")

    // log something :)
    println(s"${"!"*25} FINISH ${"!"*25}")


  }

}
