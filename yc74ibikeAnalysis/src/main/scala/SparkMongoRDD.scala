import com.mongodb.spark.MongoSpark
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author wys
  * @date 2020/6/17 - 21:05
  */
object SparkMongoRDD {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf=new SparkConf()
      .setMaster("local[*]")
      .setAppName("MongoSparkConnectorIntro")
      .set("spark.mongodb.input.uri", "mongodb://a:a@192.168.159.200:27017/yc74ibike.logs")
      .set("spark.mongodb.output.uri", "mongodb://a:a@node1:27017/yc74ibike.result")

    val sc=new SparkContext(conf)
    val docsRDD=MongoSpark.load(sc)

    docsRDD.cache()

    val r=docsRDD.collect()
    println(r.toBuffer)

    val pv=docsRDD.count()

    val uv=docsRDD.map(doc=>{
      doc.getString("openid")
    }).distinct().count()

    println("pv:"+pv+"\tuv:"+uv)

    sc.stop()

  }
}
