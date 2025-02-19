package io.github.minkik715.mkpay.money.adapter.out.persistence

import io.github.minkik715.mkpay.money.domain.*
import io.github.minkik715.mkpay.money.domain.MoneyChangingRequest.ChangingMoneyStatus
import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*


@Entity
@Table(name = "money_changing_request")
class MoneyChangingRequestJpaEntity(
    private val targetMembershipId: Long,

    @Enumerated(EnumType.STRING)
    private var moneyChangingType: MoneyChangingRequest.ChangingType,

    private val moneyAmount: Int,

    @Enumerated(EnumType.STRING)
    private var changingMoneyStatus: ChangingMoneyStatus, //enum 0: 요청 1: 성공 2: 실패

    private var uuid: String,

    @Temporal(TemporalType.TIMESTAMP)
    val timestamp: Timestamp,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var moneyChangingRequestId: Long = 0

    fun updateFirmbankingStatus(changingMoneyStatus: ChangingMoneyStatus, uuid: String){
        this.changingMoneyStatus = changingMoneyStatus
        this.uuid = uuid
    }

    fun toDomain(): MoneyChangingRequest {
        return MoneyChangingRequest.generateMoneyChangingRequest(
            MoneyChangingRequestId(moneyChangingRequestId),
            TargetMembershipId(targetMembershipId),
            ChangingTypeField(moneyChangingType),
            ChangingMoneyAmount(moneyAmount),
            ChangingMoneyStatusField(changingMoneyStatus),
            CreatedAt(Date(timestamp.time)),
            uuid
        )
    }
}