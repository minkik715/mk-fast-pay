package io.github.minkik715.mkpay.money.aggregation.adapter.`in`.web

import io.github.minkik715.mkpay.common.WebAdapter
import io.github.minkik715.mkpay.money.aggregation.application.port.`in`.GetMoneySumAddressCommand
import io.github.minkik715.mkpay.money.aggregation.application.port.`in`.GetMoneySumUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@WebAdapter
class MoneySumController(
    private val getMoneySumUseCase : GetMoneySumUseCase
) {

    @GetMapping("/money/aggregation/money-sum-by-address/{addressName}")
    fun getMoneySumByAddress(@PathVariable addressName: String): Long {
        return getMoneySumUseCase.getMoneySumByAddress(GetMoneySumAddressCommand(addressName))
    }
}