package io.github.minkik715.mkpay.remittance
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RemittanceApplication


fun main(args: Array<String>){
    runApplication<RemittanceApplication>(*args)
}