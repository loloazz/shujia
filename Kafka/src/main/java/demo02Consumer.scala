import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}

import java.util
import java.util.Properties

object demo02Consumer {
  def main(args: Array[String]): Unit = {

    /**
     * 创建消费者
     *
     */
    // 创建配置文件

    val properties = new Properties()

    //1、指定kafkabroker地址
    properties.setProperty("bootstrap.servers", "hadoop100:9092,hadoop101:9092,hadoop102:9092")

    //2、指定kv的序列化类
    properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    //指定消费者组
    //同一条数据 在同一个消费者组内只处理一次   这里的value是随便给的但是要保证唯一
    properties.setProperty("group.id", "asdasdasdasd")


    //是否自动提交消费偏移量
    properties.setProperty("enable.auto.commit", "false")

    //自动提交偏移量间隔时间
    //如果间隔时间太短会影响性能
    properties.put("auto.commit.interval.ms", "1000")


    /**
     * earliest
     * 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
     * latest
     * 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
     * none
     * topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
     *
     */


    //从最早读取数据
    properties.put("auto.offset.reset", "earliest")


    val Consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](properties)


    //订阅topic
    // 只能传java的集合

    val topics= new util.ArrayList[String]()
·
    topics.add(0,"student5")
    Consumer.subscribe(topics)





    //消费数据    Consumer.poll()

    while (true) {
      println("正在消费")

      //消费数据
      /**
       * 注意poll只能消费一次，一次就结束，所以写在死循环中
       */
      val records: ConsumerRecords[String, String] = Consumer.poll(1000)


      //获取所有数据
      val iter: util.Iterator[ConsumerRecord[String, String]] = records.iterator()


      while (iter.hasNext) {
        //一行数据   得到数据的详细信息

        val record: ConsumerRecord[String, String] = iter.next()
        val topic: String = record.topic()
        val partition: Int = record.partition()
        val offset: Long = record.offset()
        val ts: Long = record.timestamp()
        val key: String = record.key()
        val value: String = record.value()

        println(s"$topic\t$partition\t$offset\t$ts\t$key\t$value")
      }
    }

    Consumer.close()
  }




}
