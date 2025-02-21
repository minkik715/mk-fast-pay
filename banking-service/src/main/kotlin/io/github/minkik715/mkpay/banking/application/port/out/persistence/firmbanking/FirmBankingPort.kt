package io.github.minkik715.mkpay.banking.application.port.out.persistence.firmbanking

import io.github.minkik715.mkpay.banking.domain.*

interface FirmBankingPort {
    fun createFirmBankingRequest(
        fromBankName: FromBankName,
        fromBankAccountNumber: FromBankAccountNumber,
        toBankName: ToBankName,
        toBankAccountNumber: ToBankAccountNumber,
        moneyAmount: MoneyAmount,
        firmbankingStatus: FirmbankingStatus
    ): FirmbankingRequest

    fun updateFirmbankingStatus(updateFirmbankingStatusRequest: UpdateFirmbankingStatusRequest): FirmbankingRequest
}