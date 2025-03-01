package io.github.minkik715.mkpay.money.aggregation.application.port.out.svc

interface MembershipPort {
    fun getMemberIdsByAddress(addressName: String):Set<Long>
}