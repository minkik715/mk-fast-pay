package io.github.minkik715.mkpay.money.adapter.out.axon.event

import java.util.UUID

data class CreateMemberMoneyAxonEvent(
    val id: String = UUID.randomUUID().toString(),
    val membershipId: Long = 0L,
)