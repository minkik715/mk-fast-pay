package io.github.minkik715.mkpay.banking.application.port.`in`

import io.github.minkik715.common.SelfValidating
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class RegisterAccountCommand(
    val membershipId: Long,

    @field:NotBlank
    @field:Length(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val bankName: String,

    @field:NotBlank
    @field:Length(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val bankAccountNumber: String,

    val linkedStatusIsValid: Boolean,
): SelfValidating<RegisterAccountCommand>() {
    init {
        super.validateSelf()
    }
}
