package io.github.minkik715.mkpay.banking.adapter.out.persistence

import io.github.minkik715.mkpay.banking.domain.*
import jakarta.persistence.*


@Entity
@Table(name = "bank_account")
class BankAccountJpaEntity(
    val membershipId: Long,

    val bankName: String,

    val bankAccountNumber: String,

    val linkedStatusIsValid: Boolean,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var bankAccountId: Long = 0

    fun toDomain(): BankAccount {
        return BankAccount.generateBankAccount(
            BankAccountId(bankAccountId),
            MembershipId(membershipId),
            BankName(bankName),
            BankAccountNumber(bankAccountNumber),
            LinkedStatusIstValid(linkedStatusIsValid)
        )
    }
}