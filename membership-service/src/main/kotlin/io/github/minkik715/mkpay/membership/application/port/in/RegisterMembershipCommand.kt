package io.github.minkik715.mkpay.membership.application.port.`in`

import io.github.minkik715.mkpay.common.SelfValidating
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import lombok.EqualsAndHashCode
import org.hibernate.validator.constraints.Length

@EqualsAndHashCode(callSuper = false)
data class RegisterMembershipCommand(
    @field:NotBlank
    @field:Length(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val name: String,

    @field:NotBlank
    @field:Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Invalid email format")
    val email: String,

    @field:NotBlank
    @field:Length(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    val address: String,

    @field:AssertTrue
    val isValid: Boolean,

    val isCorp: Boolean,
): SelfValidating<io.github.minkik715.mkpay.membership.application.port.`in`.RegisterMembershipCommand>(){
    init {
        super.validateSelf()
    }
}