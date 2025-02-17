package io.github.minkik715.membership.application.port.`in`

import io.github.minkik715.common.SelfValidating
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotBlank
import lombok.EqualsAndHashCode

@EqualsAndHashCode(callSuper = false)
data class RegisterMembershipCommand(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val email: String,

    @field:NotBlank
    val address: String,

    @field:AssertTrue
    val isValid: Boolean,

    val isCorp: Boolean,
): SelfValidating<RegisterMembershipCommand>(){
    init {
        super.validateSelf()
    }
}