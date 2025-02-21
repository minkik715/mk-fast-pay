package io.github.minkik715.mkpay.remittance.adapter.out.persistence

import io.github.minkik715.mkpay.remittance.domain.*
import jakarta.persistence.*

@Entity
@Table(name = "request_remittance")
class RemittanceRequestJpaEntity(
    private val fromMembershipId: Long, // from membership
    private val toMembershipId: Long?, // to membership (nullable)
    private val toBankName: String?,
    private val toBankAccountNumber: String?,

    private val remittanceType: Int, // 0: membership(내부 고객), 1: bank (외부 은행 계좌)

    private val amount: Int, // 송금 요청 금액

    private var remittanceStatus: String // 상태 값 (수정 가능)
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val remittanceRequestId: Long = 0

    fun toDomain(): RemittanceRequest{
        return RemittanceRequest.generateRemittanceRequest(
            remittanceRequestId = RemittanceRequestId(remittanceRequestId),
            fromMembershipId = FromMembershipId(fromMembershipId),
            toMembershipId = ToMembershipId(toMembershipId),
            toBankName = ToBankName(toBankName),
            toBankAccountNumber = ToBankAccountNumber(toBankAccountNumber),
            remittanceType = RemittanceType(remittanceType),
            amount = Amount(amount),
            remittanceStatus = RemittanceStatus(remittanceStatus),
        )
    }

    fun updateRemittanceStatus(status: String): RemittanceRequestJpaEntity {
        this.remittanceStatus = status
        return this
    }
}