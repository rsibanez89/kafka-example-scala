package example

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import java.util.{Collections, Properties}
import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.KafkaConsumer


object KafkaExampleProducer extends App {
  println("Starting...")

  // Connects to Kafka and pushes data:
  val props = new Properties()
  props.put("bootstrap.servers","localhost:29092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("acks","all")

  val producer = new KafkaProducer[String, String](props)
  
  val topic = "rod_topic" // Manually create the topic from the UI http://localhost:8080/
  
  val record = new ProducerRecord[String, String](topic, "Message Key", "Message Value")
  val metadata = producer.send(record)
  producer.close()
}


object KafkaExampleConsumer extends App {
  println("Starting...")

  // Connects to Kafka and pushes data:
  val props = new Properties()
  props.put("group.id", "KafkaExampleConsumer")
  props.put("bootstrap.servers","localhost:29092")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("enable.auto.commit", "true")
  props.put("auto.commit.interval.ms", "1000")
  props.put("auto.offset.reset", "earliest")

  val consumer = new KafkaConsumer(props)
  val topics = List("rod_topic") // Manually create the topic from the UI http://localhost:8080/

  consumer.subscribe(topics.asJava)
  
  while(true)
  {
    val records = consumer.poll(10)
    for (record <- records.asScala) {
      println("Topic: " + record.topic() + 
              ", Key: " + record.key() +  
              ", Value: " + record.value() +
              ", Offset: " + record.offset() + 
              ", Partition: " + record.partition())
    }
  }

  consumer.close()
}

