package io.github.minkik715.mkpay.banking.adapter.out.external.bank

import io.github.minkik715.common.ExternalAdapter
import io.github.minkik715.mkpay.banking.application.port.out.BankExternalAccountPort

@ExternalAdapter
class BankExternalAccountAdapter: BankExternalAccountPort {

    override fun getBankAccountInfo(request: GetBankAccountRequest): GetBankAccountResponse {

        return GetBankAccountResponse(request.bankName, request.bankAccountNumber, true)
    }
}