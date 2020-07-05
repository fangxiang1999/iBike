import com.mongodb.spark.MongoSpark
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

/**
  * @author wys
  * @date 2020/6/17 - 19:51
  */
object SparkMongoibike {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("MongoSparkConnectorIntro")
      .config("spark.mongodb.input.uri", "mongodb://a:a@192.168.159.200:27017/yc74ibike.logs")
      .config("spark.mongodb.output.uri", "mongodb://a:a@node1:27017/yc74ibike.result")
      .getOrCreate()

    //导入MongoSpark
    val df=MongoSpark.load(spark)

    //因为已经读取了，所以直接创建视图
    df.createTempView("v_logs")

//    val pv=spark.sql("select * from v_logs")
//    println("page view：")
//    pv.show()

    df.show()

//    println("将pv和uv一次性计算出来")
//    val uv=spark.sql("select count(_id) pv,count(distinct openid) uv from v_logs")
//    uv.show()
//
//    MongoSpark.save(uv)

    spark.stop()






  }
}
