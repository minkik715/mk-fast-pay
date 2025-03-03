package io.github.minkik715.mkpay.money.query.application.port.out.svc

interface InsertMoneyChangeEventByAddress {
    fun insertMoneyIncreaseEventByAddress(addressName: String, moneyIncrease: Int)
}