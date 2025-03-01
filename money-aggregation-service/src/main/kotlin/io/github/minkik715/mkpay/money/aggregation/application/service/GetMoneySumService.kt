package io.github.minkik715.mkpay.money.aggregation.application.service

import io.github.minkik715.mkpay.common.UseCase
import io.github.minkik715.mkpay.money.aggregation.application.port.`in`.GetMoneySumAddressCommand
import io.github.minkik715.mkpay.money.aggregation.application.port.`in`.GetMoneySumUseCase
import io.github.minkik715.mkpay.money.aggregation.application.port.out.svc.MemberMoneyPort
import io.github.minkik715.mkpay.money.aggregation.application.port.out.svc.MembershipPort

@UseCase
class GetMoneySumService(
    private val membershipPort: MembershipPort,
    private val memberMoneyPort: MemberMoneyPort
): GetMoneySumUseCase {
    override fun getMoneySumByAddress(addressName: GetMoneySumAddressCommand): Long {
        val membershipIds = membershipPort.getMemberIdsByAddress(addressName.addressName)
        println(membershipIds.size)

        return membershipIds.chunked(100) // 100개씩 분할
            .sumOf { chunk -> memberMoneyPort.getMoneySumByMembershipIds(chunk.toSet()) }

    }
}