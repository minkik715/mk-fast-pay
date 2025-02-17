package io.github.minkik715.membership.application.service

import io.github.minkik715.common.UseCase
import io.github.minkik715.membership.application.port.`in`.RegisterMembershipCommand
import io.github.minkik715.membership.application.port.`in`.RegisterMembershipUseCase
import io.github.minkik715.membership.application.port.out.RegisterMembershipPort
import io.github.minkik715.membership.domain.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@UseCase
class RegisterMembershipService(
    private val registerMembershipPort: RegisterMembershipPort
): RegisterMembershipUseCase {
    override fun registerMembership(command: RegisterMembershipCommand): Membership {
        // command -> DB
        return registerMembershipPort.createMembership(
            MembershipName(command.name),
            MembershipAddress(command.address),
            MembershipEmail(command.email),
            MembershipIsValid(command.isValid),
            MembershipIsCorp(command.isCorp)
        ).toDomain()
        // biz logic -> DB
        // external system
        // port, adapter
    }
}