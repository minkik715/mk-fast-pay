package io.github.minkik715.mkpay.banking.adapter.out.persistence

import io.github.minkik715.mkpay.common.PersistenceAdapter
import io.github.minkik715.mkpay.banking.application.port.out.persistence.firmbanking.FirmBankingPort
import io.github.minkik715.mkpay.banking.application.port.out.persistence.firmbanking.UpdateFirmbankingStatusRequest
import io.github.minkik715.mkpay.banking.domain.*
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@PersistenceAdapter
class FirmbankingAdapter(
    private val springDataFirmbankingRepository: SpringDataFirmbankingRepository
): FirmBankingPort {
    override fun createFirmBankingRequest(
        fromBankName: FromBankName,
        fromBankAccountNumber: FromBankAccountNumber,
        toBankName: ToBankName,
        toBankAccountNumber: ToBankAccountNumber,
        moneyAmount: MoneyAmount,
        firmbankingStatus: FirmbankingStatus,
        aggregateIdentifier: FirmbankingRequestAggregateIdentifier?
    ): FirmbankingRequest {
        return springDataFirmbankingRepository.save(
            FirmBankingRequestJpaEntity(
                fromBankName = fromBankName.fromBankName,
                fromBankAccountNumber = fromBankAccountNumber.fromBankAccountNumber,
                toBankName = toBankName.toBankName,
                toBankAccountNumber = toBankAccountNumber.toBankAccountNumber,
                moneyAmount = moneyAmount.moneyAmount,
                firmbankingStatus = firmbankingStatus.firmbankingStatus,
                uuid = UUID.randomUUID().toString(),
                aggregateIdentifier = aggregateIdentifier?.aggregateIdentifier
            )
        ).toDomain()
    }

    override fun updateFirmbankingStatus(
        updateFirmbankingStatusRequest: UpdateFirmbankingStatusRequest
    ): FirmbankingRequest {
        val entity = springDataFirmbankingRepository.getById(updateFirmbankingStatusRequest.firmbankingRequestId.firmbankingRequestId)
        entity.updateFirmbankingStatus(updateFirmbankingStatusRequest.firmbankingStatus, updateFirmbankingStatusRequest.uuid)
        return springDataFirmbankingRepository.save(entity).toDomain()
    }

    override fun getFirmbankingByRequestId(firmbankingRequestId: FirmbankingRequestId): FirmbankingRequest? {
        return springDataFirmbankingRepository.findByIdOrNull(firmbankingRequestId.firmbankingRequestId)?.toDomain()
    }
}