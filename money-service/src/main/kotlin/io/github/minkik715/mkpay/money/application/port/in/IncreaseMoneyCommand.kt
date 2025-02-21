package io.github.minkik715.mkpay.money.application.port.`in`

import io.github.minkik715.mkpay.common.SelfValidating
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class IncreaseMoneyCommand(

    val targetMembershipId: Long,

    val amount: Int,
): SelfValidating<IncreaseMoneyCommand>() {
    init {
        super.validateSelf()
    }
}
