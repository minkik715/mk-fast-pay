package io.github.minkik715.mkpay.remittance.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
    }
}