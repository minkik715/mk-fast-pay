package io.github.minkik715.mkpay.money.adapter.out.svc

import io.github.minkik715.mkpay.common.ServiceAdapter
import io.github.minkik715.mkpay.common.feign.banking.BankingFeign
import io.github.minkik715.mkpay.money.application.port.out.svc.BankPort
import io.github.minkik715.mkpay.money.application.port.out.svc.BankValidResponse
import io.github.minkik715.mkpay.money.application.port.out.svc.RegisteredBankAccountAggregate

@ServiceAdapter
class BankServiceAdapter(
    private val bankingFeign: BankingFeign
): BankPort {
    override fun getAccountValidByMemberId(membershipId: Long): BankValidResponse? {
        return bankingFeign.getBankAccounts(membershipId).body?.first()?.let {
             BankValidResponse(it.membershipId, it.linkedStatusIsValid)
        }
    }

    override fun getRegisteredBankAccount(membershipId: Long): RegisteredBankAccountAggregate? {
        return bankingFeign.getBankAccounts(membershipId).body?.first()?.let {
            RegisteredBankAccountAggregate(
                "",
                it.bankAccountId,
                it.membershipId,
                it.bankName,
                it.bankAccountNumber
            )
        }
    }
}