package io.github.minkik715.mkpay.money.adapter.out.persistence

import io.github.minkik715.common.PersistenceAdapter
import io.github.minkik715.mkpay.money.application.port.out.MoneyPort
import io.github.minkik715.mkpay.money.application.port.out.UpdateMoneyChangingStatusRequest
import io.github.minkik715.mkpay.money.domain.*
import java.sql.Timestamp
import java.util.*

@PersistenceAdapter
class MoneyAdapter(
    private val springDataRegisteredBankAccountRepository: SpringDataMoneyChangingRepository
): MoneyPort {
    override fun createIncreaseMoneyChanging(
        targetMembershipId: TargetMembershipId,
        changingTypeField: ChangingTypeField,
        changingMoneyAmount: ChangingMoneyAmount,
        changingMoneyStatusField: ChangingMoneyStatusField,
        createdAt: CreatedAt
    ): MoneyChangingRequest {
        return springDataRegisteredBankAccountRepository.save(
            MoneyChangingRequestJpaEntity(
                targetMembershipId = targetMembershipId.targetMembershipId,
                moneyChangingType = changingTypeField.changingType,
                moneyAmount = changingMoneyAmount.changingMoneyAmount,
                changingMoneyStatus = changingMoneyStatusField.changingMoneyStatus,
                timestamp = Timestamp(createdAt.createdAt.time),
                uuid = UUID.randomUUID().toString()
            )
        ).toDomain()
    }

    override fun updateMoneyChangingStatus(updateMoneyChangingStatusRequest: UpdateMoneyChangingStatusRequest): MoneyChangingRequest {
        val entity = springDataRegisteredBankAccountRepository.getById(updateMoneyChangingStatusRequest.moneyChangingRequestId.moneyChangingRequestId)
        entity.updateFirmbankingStatus(updateMoneyChangingStatusRequest.moneyChangingType.changingMoneyStatus, updateMoneyChangingStatusRequest.uuid)
        return springDataRegisteredBankAccountRepository.save(entity).toDomain()
    }
}
