package io.github.minkik715.mkpay.money.application.port.out

import io.github.minkik715.mkpay.money.domain.ChangingMoneyStatusField
import io.github.minkik715.mkpay.money.domain.MoneyChangingRequestId

data class UpdateMoneyChangingStatusRequest(
    val moneyChangingRequestId: MoneyChangingRequestId,
    val moneyChangingType: ChangingMoneyStatusField,
    val uuid: String,
)
