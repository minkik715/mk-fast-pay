package io.github.minkik715.membership.application.port.`in`

import io.github.minkik715.common.SelfValidating
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotBlank
import lombok.EqualsAndHashCode
import org.jetbrains.annotations.NotNull

@EqualsAndHashCode(callSuper = false)
data class ModifyMembershipCommand(
    val id: Long,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val email: String,

    @field:NotBlank
    val address: String,

    @NotNull
    val isValid: Boolean,

    @NotNull
    val isCorp: Boolean,
): SelfValidating<ModifyMembershipCommand>(){
    init {
        super.validateSelf()
    }
}