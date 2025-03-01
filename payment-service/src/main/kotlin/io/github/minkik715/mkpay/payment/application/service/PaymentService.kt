package io.github.minkik715.mkpay.payment.application.service

import io.github.minkik715.mkpay.common.UseCase
import io.github.minkik715.mkpay.payment.application.port.`in`.PaymentUseCase
import io.github.minkik715.mkpay.payment.application.port.`in`.RequestPaymentCommand
import io.github.minkik715.mkpay.payment.application.port.out.PaymentPort
import io.github.minkik715.mkpay.payment.application.port.out.svc.MembershipPort
import io.github.minkik715.mkpay.payment.domain.*

@UseCase
class PaymentService(
    private val paymentPort: PaymentPort,
    private val membershipPort: MembershipPort
): PaymentUseCase {
    override fun createPayment(command: RequestPaymentCommand): Payment {
        //valid 처리
        val membershipValidResponse = membershipPort.getMembershipByMemberId(command.membershipId)

        if(membershipValidResponse?.isValid != true) {
            throw IllegalArgumentException("Invalid membership id ${command.membershipId}")
        }

        return paymentPort.requestPayment(
            MembershipId(command.membershipId),
            Price(command.price),
            FranchiseId(command.franchiseId),
            FranchiseFeeRate(command.franchiseFeeRate)
        )
    }
}