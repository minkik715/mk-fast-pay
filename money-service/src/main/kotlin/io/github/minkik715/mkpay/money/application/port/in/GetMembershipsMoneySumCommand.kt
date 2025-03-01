package io.github.minkik715.mkpay.money.application.port.`in`

data class GetMembershipsMoneySumCommand(
    val membershipIds: List<Long>,
)