package io.github.minkik715.mkpay.membership

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MembershipApplication


fun main(args: Array<String>){
    runApplication<io.github.minkik715.mkpay.membership.MembershipApplication>(*args)
}