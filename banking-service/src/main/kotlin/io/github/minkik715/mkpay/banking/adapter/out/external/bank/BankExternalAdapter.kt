package io.github.minkik715.mkpay.banking.adapter.out.external.bank

import io.github.minkik715.mkpay.common.ExternalAdapter
import io.github.minkik715.mkpay.banking.application.port.out.*

@ExternalAdapter
class BankExternalAdapter: BankExternalPort {

    override fun getBankAccountInfo(request: GetBankAccountRequest): GetBankAccountResponse {

        return GetBankAccountResponse(request.bankName, request.bankAccountNumber, true)
    }

    override fun requestExternalFirmbanking(request: FirmbankingExternalRequest): FirmbankingExternalResponse {
        return FirmbankingExternalResponse(0)
    }
}