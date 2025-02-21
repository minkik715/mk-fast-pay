package io.github.minkik715.mkpay.taskconsumer

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class TaskResultProducer(
    @Value("\${kafka.clusters.bootstrap-servers}") private val kafkaBootstrapServers: String,
    @Value("\${task.result.topic}") private val topic: String,
    private val objectMapper: ObjectMapper,

) {

    lateinit var producer: KafkaProducer<String, String>

    init {
        val props = Properties()
        props["bootstrap.servers"] = kafkaBootstrapServers
        props["key.serializer"] = org.apache.kafka.common.serialization.StringSerializer::class.java.canonicalName
        props["value.serializer"] = org.apache.kafka.common.serialization.StringSerializer::class.java.canonicalName
        this.producer = KafkaProducer(props)
    }


    fun<T> sendResult(key: String, value: T) {

        val jsonValue = objectMapper.writeValueAsString(value)
        val record = ProducerRecord(topic, key, jsonValue)
        producer.send(record, { recordMetadata, exception ->
            if(exception != null){
                println("FAILED TO SEND MESSAGE! ${exception.message}")

            }else{
                println("Message sent successfully. Offset: ${recordMetadata.offset()}")
            }
        })
    }
}