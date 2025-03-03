package io.github.minkik715.mkpay.money.query.application.service

import io.github.minkik715.mkpay.common.UseCase
import io.github.minkik715.mkpay.money.query.adapter.axon.QueryMoneySumByAddress
import io.github.minkik715.mkpay.money.query.application.port.`in`.QueryMoneySumByRegionQuery
import io.github.minkik715.mkpay.money.query.application.port.`in`.QueryMoneySumByRegionUseCase
import io.github.minkik715.mkpay.money.query.domain.MoneySumByRegion
import org.axonframework.queryhandling.QueryGateway
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional
class QueryMoneySumByRegionService(
    private val queryGateway: QueryGateway
): QueryMoneySumByRegionUseCase {
    override fun queryMoneySumByRegion(queryMoneySumByRegionQuery: QueryMoneySumByRegionQuery): MoneySumByRegion {
        return queryGateway.query(QueryMoneySumByAddress(queryMoneySumByRegionQuery.address), MoneySumByRegion::class.java).join()
    }
}