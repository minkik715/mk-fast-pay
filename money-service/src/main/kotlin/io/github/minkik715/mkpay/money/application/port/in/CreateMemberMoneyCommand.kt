package io.github.minkik715.mkpay.money.application.port.`in`

import io.github.minkik715.mkpay.common.SelfValidating
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class CreateMemberMoneyCommand(
    val membershipId: Long,
): SelfValidating<CreateMemberMoneyCommand>() {
    init {
        super.validateSelf()
    }
}
