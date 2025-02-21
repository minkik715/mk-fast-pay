package io.github.minkik715.mkpay.taskconsumer

import RechargingMoneyTask
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class TaskConsumer(
    @Value("\${kafka.clusters.bootstrap-servers}") private val kafkaBootstrapServers: String,
    @Value("\${task.topic}") private val topic: String,
    private val objectMapper: ObjectMapper,
    private val taskResultProducer: TaskResultProducer
) {

    lateinit var consumer: KafkaConsumer<String, String>

    init {
        val props = Properties()
        props["bootstrap.servers"] = kafkaBootstrapServers
        props["group.id"] =  "my-group";
        props["key.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
        props["value.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
        this.consumer = KafkaConsumer(props)

        consumer.subscribe(listOf(topic))

        val consumerThread = Thread{
            while (true) {
                val records = consumer.poll(java.time.Duration.ofSeconds(1))

                for (record in records) {
                    // task run
                    val task = objectMapper.readValue(record.value(), RechargingMoneyTask::class.java)

                    val subTasks = task.subTasks

                    for (subTask in subTasks) {
                        //subtask validate 확인

                        subTask.updateStatus("success")
                    }

                    // task result



                    // produce TaskResult
                    taskResultProducer.sendResult<RechargingMoneyTask>(task.taskId, task)
                }
            }
        }
        consumerThread.start()
    }
}