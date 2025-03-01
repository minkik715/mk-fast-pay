package io.github.minkik715.mkpay.common.feign.money

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient (name="money-service", url = "http://money-service:8080")
@Component
interface MoneyFeign {
    @PostMapping("/memberships/money-sum")
    fun getMoneySumByMemberIds(@RequestBody request: GetMembershipsMoneySumFeignRequest): Long
}