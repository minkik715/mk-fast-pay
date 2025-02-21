package io.github.minkik715.mkpay.banking.application.port.`in`

import io.github.minkik715.mkpay.common.SelfValidating
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class FirmbankingRequestCommand(

    @field:NotBlank
    @field:Length(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val fromBankName: String,

    @field:NotBlank
    @field:Length(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val fromBankAccountNumber:String,

    @field:NotBlank
    @field:Length(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val toBankName: String,

    @field:NotBlank
    @field:Length(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val toBankAccountNumber:String,

    val moneyAmount: Int
): SelfValidating<FirmbankingRequestCommand>() {
    init {
        super.validateSelf()
    }
}
