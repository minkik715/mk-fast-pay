package io.github.minkik715.membership.application.port.`in`

import io.github.minkik715.common.UseCase
import io.github.minkik715.membership.domain.Membership

interface RegisterMembershipUseCase {
    fun registerMembership(command: RegisterMembershipCommand): Membership
}