package io.github.minkik715.mkpay.money.application.port.out

import RechargingMoneyTask

interface SendRechargingMoneyTaskPort {
    fun sendRechargingMoneyTaskPort(task: RechargingMoneyTask)
}