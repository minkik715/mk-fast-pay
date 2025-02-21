package io.github.minkik715.mkpay.remittance.adapter.out.persistence

import io.github.minkik715.mkpay.common.PersistenceAdapter
import io.github.minkik715.mkpay.remittance.application.port.out.RemittancePort
import io.github.minkik715.mkpay.remittance.domain.*

@PersistenceAdapter
class RemittancePersistenceAdapter(
    private val springDataRemittanceRequestRepository: SpringDataRemittanceRequestRepository
) : RemittancePort {

    override fun createRemittanceRequestHistory(
        fromMembershipId: FromMembershipId,
        toMembershipId: ToMembershipId,
        toBankName: ToBankName,
        toBankAccountNumber: ToBankAccountNumber,
        remittanceType: RemittanceType,
        amount: Amount,
        remittanceStatus: RemittanceStatus,
    ): RemittanceRequest {
        return springDataRemittanceRequestRepository.save(
            RemittanceRequestJpaEntity(
                fromMembershipId = fromMembershipId.fromMembershipId,
                toMembershipId = toMembershipId.toMembershipId,
                toBankName = toBankName.toBankName,
                toBankAccountNumber = toBankAccountNumber.toBankAccountNumber,
                remittanceType = remittanceType.remittanceType,
                amount = amount.amount,
                remittanceStatus = remittanceStatus.remittanceStatus
            )
        ).toDomain()

    }

    override fun updateRemittanceStatus(
        remittanceRequestId: RemittanceRequestId,
        status: RemittanceStatus
    ): RemittanceRequest {
        val entity =
            (springDataRemittanceRequestRepository.findByRemittanceRequestId(remittanceRequestId.remittanceRequestId)
                ?.let { it.updateRemittanceStatus(status.remittanceStatus) }
                ?: throw IllegalArgumentException("remittanceRequestId $remittanceRequestId not found"))

        return springDataRemittanceRequestRepository.save(entity).toDomain()
    }
}