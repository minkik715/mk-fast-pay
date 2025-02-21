package io.github.minkik715.mkpay.remittance.application.port.`in`

import io.github.minkik715.mkpay.common.SelfValidating
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class RequestRemittanceCommand(
    val fromMembershipId: Long, // from membership
    val toMembershipId: Long?,  // to membership (nullable)
    val toBankName: String?,
    val toBankAccountNumber: String?,
    val remittanceType: Int, // 0: membership(내부 고객), 1: bank (외부 은행 계좌)

    @NotNull
    @NotBlank
    val amount: Int // 송금 요청 금액
) : SelfValidating<RequestRemittanceCommand>() {

    init {
        this.validateSelf()
    }
}