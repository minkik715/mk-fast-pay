package io.github.minkik715.mkpay.remittance.adapter.out.svc

import io.github.minkik715.mkpay.common.ServiceAdapter
import io.github.minkik715.mkpay.remittance.application.port.out.money.MoneyInfo
import io.github.minkik715.mkpay.remittance.application.port.out.money.MoneyPort

@ServiceAdapter
class MoneyServiceAdapter: MoneyPort {
    override fun getMoneyInfo(membershipId: Long): MoneyInfo {
        return MoneyInfo(membershipId, 999999)
    }

    override fun requestMoneyRecharging(membershipId: Long, amount: Int): Boolean {
        return true
    }

    override fun requestMoneyIncrease(membershipId: Long, amount: Int): Boolean {
        return true
    }

    override fun requestMoneyDecrease(membershipId: Long, amount: Int): Boolean {
        return true
    }
}