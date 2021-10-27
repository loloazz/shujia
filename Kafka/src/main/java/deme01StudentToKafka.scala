import org.apache.kafka.clients.producer.{KafkaProducer, Producer, ProducerRecord}

import java.util.Properties
import scala.io.{BufferedSource, Source}

object deme01StudentToKafka {
  def main(args: Array[String]): Unit = {

    /**
     * 1、创佳生产者
     *
     */


    // 先创建配置文件对象

    val properties = new Properties()

    //1、指定kafkabroker地址
    properties.setProperty("bootstrap.servers", "hadoop100:9092,hadoop101:9092,hadoop102:9092")

    //2、指定kv的序列化类
    properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")




    // 创建kafka的生产者对象
    val producer = new KafkaProducer[String, String](properties)

    val studentsData: BufferedSource = Source.fromFile("data/students.txt")

    val lines: Iterator[String] = studentsData.getLines()

    lines.foreach(line => {
      val id: String = line.split(",")(0)

      //将不同的性别写入不同分区
      val gender: String = line.split(",")(3)


      val partionNum: Int = gender match {
        case "男" => 1
        case _ => 0

      }


      val record = new ProducerRecord[String, String]("student5", partionNum, id, line)
      // def send(record: ProducerRecord[K, V]): Future[RecordMetadata] =
      // { return send(record, null) }
      // 注意传参   是   ProducerRecord对象

      // 发送
      producer.send(record)

      // 刷写
      producer.flush()


    })

    // 关闭资源
    producer.close()
    //kafka-topics.sh --create --zookeeper hadoop100:2181,hadoop101:2181,hadoop102:2181 --replication-factor 2 --partitions 2 --topic student4
  }
}
