package io.github.minkik715.mkpay.money.application.port.out.svc

data class BankValidResponse(
    val membershipId: Long,
    val isValid: Boolean
)