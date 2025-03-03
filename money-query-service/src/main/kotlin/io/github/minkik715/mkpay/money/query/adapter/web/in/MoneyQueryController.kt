package io.github.minkik715.mkpay.money.query.adapter.web.`in`

import io.github.minkik715.mkpay.common.WebAdapter
import io.github.minkik715.mkpay.money.query.application.port.`in`.QueryMoneySumByRegionQuery
import io.github.minkik715.mkpay.money.query.application.port.`in`.QueryMoneySumByRegionUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
class MoneyQueryController(
    private val queryMoneySumByRegionUseCase: QueryMoneySumByRegionUseCase
) {

    @GetMapping("/money/aggregation/money-sum-by-address/{addressName}")
    fun getMoneySumByAddress(@PathVariable addressName: String): Long {
        return queryMoneySumByRegionUseCase.queryMoneySumByRegion(QueryMoneySumByRegionQuery(addressName)).moneySum
    }
}