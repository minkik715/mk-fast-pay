package io.github.minkik715.mkpay.banking.adapter.out.persistence

import io.github.minkik715.mkpay.banking.domain.*
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "firmbanking_request")
class FirmBankingRequestJpaEntity(
    private val toBankName: String,

    private val toBankAccountNumber: String,

    private val fromBankName: String,

    private val fromBankAccountNumber: String,

    private val moneyAmount: Int,

    private var firmbankingStatus: Int,

    private var uuid: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var requestFirmbankingId: Long = 0


    fun updateFirmbankingStatus(firmbankingStatus: FirmbankingStatus, uuid: String){
        this.firmbankingStatus = firmbankingStatus.firmbankingStatus
        this.uuid = uuid
    }

    fun toDomain(): FirmbankingRequest {
        return FirmbankingRequest.generateFirmbanking(
            FirmbankingRequestId(requestFirmbankingId),
            FromBankName(fromBankName),
            FromBankAccountNumber(fromBankAccountNumber),
            ToBankName(toBankName),
            ToBankAccountNumber(toBankAccountNumber),
            MoneyAmount(moneyAmount),
            FirmbankingStatus(firmbankingStatus),
            uuid
        )
    }
}