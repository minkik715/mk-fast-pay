package io.github.minkik715.mkpay.remittance.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@ComponentScan("io.github.minkik715.mkpay.common")
@EnableFeignClients
class RemittanceConfig {
}