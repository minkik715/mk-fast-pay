package io.github.minkik715.mkpay.common.feign.banking

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient (name="banking-service", url = "http://banking-service:8080")
@Component
interface BankingFeign {
    @GetMapping("/bank-accounts/{membershipId}")
    fun getMembershipByMemberId(@PathVariable("membershipId") membershipId: Long): ResponseEntity<List<BankAccountFeignResponse>>
}