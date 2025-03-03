package io.github.minkik715.mkpay.money.query.application.port.`in`

import io.github.minkik715.mkpay.money.query.domain.MoneySumByRegion

interface QueryMoneySumByRegionUseCase {
    fun queryMoneySumByRegion(queryMoneySumByRegionQuery: QueryMoneySumByRegionQuery): MoneySumByRegion
}