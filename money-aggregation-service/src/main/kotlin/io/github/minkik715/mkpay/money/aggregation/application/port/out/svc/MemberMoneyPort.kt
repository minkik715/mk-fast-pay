package io.github.minkik715.mkpay.money.aggregation.application.port.out.svc

import org.springframework.web.bind.annotation.PathVariable

interface MemberMoneyPort {
    fun getMoneySumByMembershipIds(membershipIds: Set<Long>): Long
}