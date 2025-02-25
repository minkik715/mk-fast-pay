package io.github.minkik715.mkpay.money.adapter.out.axon.event

class IncreaseMoneyAxonEvent (
    val targetMembershipId: Long = 0,
    val amount: Int = 0,
    val aggregateIdentifier: String = ""
)