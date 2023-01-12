package helpers

import configs.{ConfigElement, DataFileConfig}
import org.apache.spark.sql.{DataFrame, Dataset}
import org.apache.spark.sql.types.{BooleanType, DataType, DateType, DoubleType, FloatType, IntegerType, LongType, StringType, StructField, StructType}
import spray.json._

object Helper {

  def sparkSchemeFromJSON(): StructType = {
    // Basic type mapping map
    val stringToType = Map[String, DataType](
      "String" -> StringType,
      "Double" -> DoubleType,
      "Float" -> FloatType,
      "Int" -> IntegerType,
      "Boolean" -> BooleanType,
      "Long" -> LongType,
      "DateTime" -> DateType
    )

    // Load JSON From file
    val source = scala.io.Source.fromFile("src/config/DataFileConfig.json")
    val lines = try source.mkString finally source.close()
    val json = lines.parseJson

    // Parse to case class
    import configs.JsonProtocol._
    val datafileConfig = json.convertTo[DataFileConfig[ConfigElement]]

    // Convert case class to StructType
    var structSeq: Seq[StructField] = Seq()
    datafileConfig.columns.foreach(configElement => {
      structSeq = structSeq :+ StructField(configElement.name, stringToType(configElement.typeOf), nullable = false)
    })
    StructType(structSeq)
  }

  def dataframeToDataset(df : DataFrame): Dataset[String] = {
    val encoder = org.apache.spark.sql.catalyst.encoders.ExpressionEncoder[String]
    df.as(encoder)
  }
}

