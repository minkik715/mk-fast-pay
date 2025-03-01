package io.github.minkik715.mkpay.money.aggregation.adapter.out.svc

import io.github.minkik715.mkpay.common.ServiceAdapter
import io.github.minkik715.mkpay.common.feign.money.GetMembershipsMoneySumFeignRequest
import io.github.minkik715.mkpay.common.feign.money.MoneyFeign
import io.github.minkik715.mkpay.money.aggregation.application.port.out.svc.MemberMoneyPort

@ServiceAdapter
class MemberMoneyService(
    private val memberMoneyFeign: MoneyFeign
): MemberMoneyPort {
    override fun getMoneySumByMembershipIds(membershipIds: Set<Long>): Long {
        return memberMoneyFeign.getMoneySumByMemberIds(GetMembershipsMoneySumFeignRequest(membershipIds))
    }
}