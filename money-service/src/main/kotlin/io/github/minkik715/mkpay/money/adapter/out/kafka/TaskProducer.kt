package io.github.minkik715.mkpay.money.adapter.out.kafka

import RechargingMoneyTask
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.minkik715.mkpay.money.application.port.out.SendRechargingMoneyTaskPort
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class TaskProducer(
    @Value("\${kafka.clusters.bootstrap-servers}") private val kafkaBootstrapServers: String,
    @Value("\${task.topic}") private val topic: String,
    private val objectMapper: ObjectMapper
):SendRechargingMoneyTaskPort {

    lateinit var producer: KafkaProducer<String, String>

    init {
        val props = Properties()
        props["bootstrap.servers"] = kafkaBootstrapServers
        props["key.serializer"] = org.apache.kafka.common.serialization.StringSerializer::class.java.canonicalName
        props["value.serializer"] = org.apache.kafka.common.serialization.StringSerializer::class.java.canonicalName
        this.producer = KafkaProducer(props)
    }

    override fun sendRechargingMoneyTaskPort(task: RechargingMoneyTask) {
        sendMessage(task.taskId, task)
    }

    private fun sendMessage(key: String, value: RechargingMoneyTask) {

        val jsonString = objectMapper.writeValueAsString(value)
        val record = ProducerRecord(topic, key, jsonString)
        producer.send(record, { recordMetadata, exception ->
            if(exception != null){
                println("FAILED TO SEND MESSAGE! ${exception.message}")

            }else{
                println("Message sent successfully. Offset: ${recordMetadata.offset()}")
            }
        })
    }
}