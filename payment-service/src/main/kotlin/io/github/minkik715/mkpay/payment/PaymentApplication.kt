package io.github.minkik715.mkpay.payment
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PaymentApplication


fun main(args: Array<String>){
    runApplication<PaymentApplication>(*args)
}