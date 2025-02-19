package io.github.minkik715.mkpay.money.adapter.`in`.web

data class IncreaseMoneyRequest(
    val targetMembershipId: Long,
    val amount: Int,
)