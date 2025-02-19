package io.github.minkik715.mkpay.money.domain

import io.github.minkik715.mkpay.money.domain.MoneyChangingRequest.ChangingMoneyStatus
import io.github.minkik715.mkpay.money.domain.MoneyChangingRequest.ChangingType
import java.util.*

class MoneyChangingRequest private constructor(
    val moneyChangingRequestId: Long,

    val targetMembershipId: Long,

    val changingType: ChangingType, //enum 0:증액 1:감액

    val changingMoneyAmount: Int,

    val changingMoneyStatus: ChangingMoneyStatus, //enum 0: 요청 1: 성공 2: 실패

    val createdAt: Date,

    val uuid: String,
){

    enum class ChangingType{
        INCREASING,
        DECREASING
    }

    enum class ChangingMoneyStatus{
        REQUESTED,
        SUCCEEDED,
        FAILED,
        CANCELED
    }



    companion object {
        fun generateMoneyChangingRequest(
            moneyChangingRequestId: MoneyChangingRequestId,
            targetMembershipId: TargetMembershipId,
            changingType: ChangingTypeField,
            changingMoneyAmount: ChangingMoneyAmount,
            changingMoneyStatus: ChangingMoneyStatusField,
            createdAt: CreatedAt,
            uuid: String,
        ): MoneyChangingRequest {
            return MoneyChangingRequest(
                moneyChangingRequestId = moneyChangingRequestId.moneyChangingRequestId,
                targetMembershipId = targetMembershipId.targetMembershipId,
                changingType = changingType.changingType,
                changingMoneyAmount = changingMoneyAmount.changingMoneyAmount,
                changingMoneyStatus = changingMoneyStatus.changingMoneyStatus,
                createdAt = createdAt.createdAt,
                uuid =uuid
            )
        }
    }

}

@JvmInline
value class MoneyChangingRequestId constructor(val moneyChangingRequestId: Long)
@JvmInline
value class TargetMembershipId constructor(val targetMembershipId: Long)
@JvmInline
value class ChangingTypeField constructor(val changingType: ChangingType)
@JvmInline
value class ChangingMoneyAmount constructor(val changingMoneyAmount: Int)
@JvmInline
value class ChangingMoneyStatusField constructor(val changingMoneyStatus: ChangingMoneyStatus)
@JvmInline
value class CreatedAt constructor(val createdAt: Date)
