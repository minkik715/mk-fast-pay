package io.github.minkik715.mkpay.banking.domain

class BankAccount private constructor(
    val bankAccountId: Long,

    val membershipId: Long,

    val bankName: String,

    val bankAccountNumber: String,

    val linkedStatusIsValid: Boolean,
){

    companion object {
        fun generateBankAccount(
            bankAccountId: BankAccountId,
            membershipId: MembershipId,
            bankName: BankName,
            bankAccountNumber: BankAccountNumber,
            linkedStatusIsValid: LinkedStatusIstValid
        ): BankAccount {
            return BankAccount(
                bankAccountId = bankAccountId.bankAccountId,
                membershipId = membershipId.membershipId,
                bankName = bankName.bankName,
                bankAccountNumber = bankAccountNumber.backAccountNumber,
                linkedStatusIsValid = linkedStatusIsValid.linkedStatusIsValid
            )
        }
    }

}

@JvmInline
value class BankAccountId constructor(val bankAccountId: Long)
@JvmInline
value class MembershipId constructor(val membershipId: Long)
@JvmInline
value class BankName constructor(val bankName: String)
@JvmInline
value class BankAccountNumber constructor(val backAccountNumber: String)
@JvmInline
value class LinkedStatusIstValid constructor(val linkedStatusIsValid: Boolean)
