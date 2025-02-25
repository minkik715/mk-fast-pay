package io.github.minkik715.mkpay.banking.application.port.`in`

import io.github.minkik715.mkpay.banking.domain.FirmbankingRequest

interface FirmbankingUseCase {
    fun requestFirmbanking(command: FirmbankingRequestCommand): FirmbankingRequest?
    fun requestFirmbankingByEvent(command: FirmbankingRequestCommand)
    fun updateFirmbankingByEvent(command: FirmbankingUpdateCommand)

    //fun getBankAccounts(command: FindBankAccountsCommand): List<BankAccount>
}