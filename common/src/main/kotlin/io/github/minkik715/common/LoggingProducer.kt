package io.github.minkik715.common

import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class LoggingProducer(
        @Value("\${kafka.clusters.bootstrap-servers}") private val kafkaBootstrapServers: String,
        @Value("\${logging.topic}") private val topic: String,
        ) {

        var producer: KafkaProducer<String, String>

        init {
                val props = Properties()
                props["bootstrap.servers"] = kafkaBootstrapServers
                props["key.serializer"] = org.apache.kafka.common.serialization.StringSerializer::class.java.canonicalName
                props["value.serializer"] = org.apache.kafka.common.serialization.StringSerializer::class.java.canonicalName
                this.producer = KafkaProducer(props)
        }

        fun sendMessage(key: String, value: String) {
                val record = ProducerRecord(topic, key, value)
                producer.send(record, { recordMetadata, exception ->
                        if(exception != null){
                                println("FAILED TO SEND MESSAGE! ${exception.message}")

                        }else{
                                println("Message sent successfully. Offset: ${recordMetadata.offset()}")
                        }
                })
        }
}