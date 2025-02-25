package io.github.minkik715.mkpay.money.application.port.out

import com.google.type.Money
import io.github.minkik715.mkpay.money.domain.*

interface MoneyPort {
    fun createIncreaseMoneyChanging(
        targetMembershipId: TargetMembershipId,
        changingTypeField: ChangingTypeField,
        changingMoneyAmount: ChangingMoneyAmount,
        changingMoneyStatusField: ChangingMoneyStatusField,
        createdAt: CreatedAt,
    ): MoneyChangingRequest

    fun updateMoneyChangingStatus(updateMoneyChangingStatusRequest: UpdateMoneyChangingStatusRequest): MoneyChangingRequest

}