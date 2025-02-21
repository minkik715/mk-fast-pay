package io.github.minkik715.mkpay.remittance.domain

class RemittanceRequest private constructor(
    val remittanceRequestId: Long,
    val fromMembershipId: Long,
    val toMembershipId: Long?,
    val toBankName: String?,
    val toBankAccountNumber: String?,
    val remittanceType: Int, // 0: membership(내부 고객), 1: bank (외부 은행 계좌)
    val amount: Int,
    var remittanceStatus: String
) {

    companion object {
        fun generateRemittanceRequest(
            remittanceRequestId: RemittanceRequestId,
            fromMembershipId: FromMembershipId,
            toMembershipId: ToMembershipId,
            toBankName: ToBankName,
            toBankAccountNumber: ToBankAccountNumber,
            remittanceType: RemittanceType,
            amount: Amount,
            remittanceStatus: RemittanceStatus
        ): RemittanceRequest {
            return RemittanceRequest(
                remittanceRequestId.remittanceRequestId,
                fromMembershipId.fromMembershipId,
                toMembershipId.toMembershipId,
                toBankName.toBankName,
                toBankAccountNumber.toBankAccountNumber,
                remittanceType.remittanceType,
                amount.amount,
                remittanceStatus.remittanceStatus
            )
        }
    }
}

@JvmInline
value class RemittanceRequestId constructor(val remittanceRequestId: Long)

@JvmInline
value class FromMembershipId constructor(val fromMembershipId: Long)
@JvmInline
value class ToMembershipId constructor(val toMembershipId: Long?)

@JvmInline
value class ToBankName constructor(val toBankName: String?)

@JvmInline
value class ToBankAccountNumber constructor(val toBankAccountNumber: String?)

@JvmInline
value class RemittanceType constructor(val remittanceType: Int)

@JvmInline
value class Amount constructor(val amount: Int)

@JvmInline
value class RemittanceStatus constructor(val remittanceStatus: String)
