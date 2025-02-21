package io.github.minkik715.mkpay.remittance.adapter.out.svc

import io.github.minkik715.mkpay.common.ServiceAdapter
import io.github.minkik715.mkpay.remittance.application.port.out.banking.BankingInfo
import io.github.minkik715.mkpay.remittance.application.port.out.banking.BankingPort

@ServiceAdapter
class BankingServiceAdapter: BankingPort {
    override fun getMembershipBankingInfo(bankName: String, bankAccountNumber: String): BankingInfo {
        return BankingInfo(bankName, bankAccountNumber, true)
    }

    override fun requestFirmbanking(bankName: String, bankAccountNumber: String, amount: Int): Boolean {
        return true
    }
}