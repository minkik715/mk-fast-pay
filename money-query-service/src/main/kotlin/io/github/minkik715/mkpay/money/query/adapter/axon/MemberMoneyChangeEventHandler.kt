package io.github.minkik715.mkpay.money.aggregation.adapter.axon

import io.github.minkik715.mkpay.common.`interface`.axon.event.RequestFirmbankingFinishedEvent
import io.github.minkik715.mkpay.money.query.application.port.out.svc.InsertMoneyChangeEventByAddress
import io.github.minkik715.mkpay.money.query.application.port.out.svc.MembershipPort
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class MemberMoneyChangeEventHandler {

    @EventHandler
    fun handler(event: RequestFirmbankingFinishedEvent, membershipPort: MembershipPort,
                insertMoneyChangeEventByAddress: InsertMoneyChangeEventByAddress
    ){
        println(event.toString())

        val membershipAddress = membershipPort.getMembershipAddressByMemberId(event.membershipId)
            ?: throw IllegalArgumentException("membership ${event.membershipId} address not found")

        val address = membershipAddress.address
        val moneyIncrease = event.moneyAmount

        insertMoneyChangeEventByAddress.insertMoneyIncreaseEventByAddress(address, moneyIncrease)
    }
}