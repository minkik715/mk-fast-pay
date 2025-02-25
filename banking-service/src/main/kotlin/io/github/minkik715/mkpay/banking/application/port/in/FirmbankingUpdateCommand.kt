package io.github.minkik715.mkpay.banking.application.port.`in`

import io.github.minkik715.mkpay.common.SelfValidating
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class FirmbankingUpdateCommand(

    val firmbankingRequestId: Long,

    val status: Int
): SelfValidating<FirmbankingUpdateCommand>() {
    init {
        super.validateSelf()
    }
}
