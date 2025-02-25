package io.github.minkik715.mkpay.banking.domain


class FirmbankingRequest private constructor(
    val firmbankingRequestId: Long,
    val fromBankName:String,
    val fromBankAccountNumber: String,
    val toBankName:String,
    val toBankAccountNumber: String,
    val moneyAmount: Int,
    val firmbankingStatus: Int, //0: 요청, 1:완료, 2:실패
    val uuid: String,
    val aggregateIdentifier: String,
){
    companion object {
        fun generateFirmbanking(
            firmbankingRequestId: FirmbankingRequestId,
            fromBankName: FromBankName,
            fromBankAccountNumber: FromBankAccountNumber,
            toBankName: ToBankName,
            toBankAccountNumber: ToBankAccountNumber,
            moneyAmount: MoneyAmount,
            firmbankingStatus: FirmbankingStatus,
            uuid: String,
            aggregateIdentifier: FirmbankingRequestAggregateIdentifier = FirmbankingRequestAggregateIdentifier("")
        ): FirmbankingRequest {
            return FirmbankingRequest(
                firmbankingRequestId = firmbankingRequestId.firmbankingRequestId,
                fromBankName = fromBankName.fromBankName,
                fromBankAccountNumber = fromBankAccountNumber.fromBankAccountNumber,
                toBankName = toBankName.toBankName,
                toBankAccountNumber = toBankAccountNumber.toBankAccountNumber,
                moneyAmount = moneyAmount.moneyAmount,
                firmbankingStatus = firmbankingStatus.firmbankingStatus,
                uuid,
                aggregateIdentifier = aggregateIdentifier.aggregateIdentifier
            )
        }
    }
}
@JvmInline
value class FirmbankingRequestId(val firmbankingRequestId: Long)
@JvmInline
value class FromBankName constructor(val fromBankName: String)
@JvmInline
value class FromBankAccountNumber constructor(val fromBankAccountNumber: String)
@JvmInline
value class ToBankName constructor(val toBankName: String)
@JvmInline
value class ToBankAccountNumber constructor(val toBankAccountNumber: String)
@JvmInline
value class MoneyAmount constructor(val moneyAmount: Int)
@JvmInline
value class FirmbankingStatus constructor(val firmbankingStatus: Int)
@JvmInline
value class FirmbankingRequestAggregateIdentifier constructor(val aggregateIdentifier: String)

