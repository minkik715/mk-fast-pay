package io.github.minkik715.mkpay.money.adapter.`in`.kafka

import RechargingMoneyTask
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.minkik715.mkpay.common.CountDownLatchManger
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class RechargingMoneyResultConsumer(
    @Value("\${kafka.clusters.bootstrap-servers}") private val kafkaBootstrapServers: String,
    @Value("\${task.result.topic}") private val topic: String,
    private val objectMapper: ObjectMapper,
    private val countDownLatchManger: CountDownLatchManger
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

                    var taskResult = true
                    val task = objectMapper.readValue(record.value(), RechargingMoneyTask::class.java)

                    val subTasks = task.subTasks

                    for (subTask in subTasks) {
                        if(subTask.status == "fail"){
                            taskResult = false
                            break
                        }
                    }

                    if(taskResult){
                        countDownLatchManger.setDataForKey(task.taskId, "success")
                    }else{
                        countDownLatchManger.setDataForKey(task.taskId, "fail")
                    }
                    countDownLatchManger.getCountDownLatch(task.taskId)?.let {
                        it.countDown()
                    } ?: throw IllegalArgumentException("no task found ${task.taskId}")
                }
            }
        }
        consumerThread.start()
    }
}