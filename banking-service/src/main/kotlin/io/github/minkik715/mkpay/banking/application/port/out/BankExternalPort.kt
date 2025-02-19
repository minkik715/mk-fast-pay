package io.github.minkik715.mkpay.banking.application.port.out

interface BankExternalPort {
    fun getBankAccountInfo(request: GetBankAccountRequest): GetBankAccountResponse

    fun requestExternalFirmbanking(request: FirmbankingExternalRequest): FirmbankingExternalResponse
}