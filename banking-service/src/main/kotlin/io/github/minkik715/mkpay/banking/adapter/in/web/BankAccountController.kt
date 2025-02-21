package io.github.minkik715.mkpay.banking.adapter.`in`.web

import io.github.minkik715.mkpay.common.WebAdapter
import io.github.minkik715.mkpay.banking.application.port.`in`.BankAccountUseCase
import io.github.minkik715.mkpay.banking.application.port.`in`.FindBankAccountsCommand
import io.github.minkik715.mkpay.banking.application.port.`in`.RegisterAccountCommand
import io.github.minkik715.mkpay.banking.domain.BankAccount
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@WebAdapter
@RestController
class BankAccountController(
    private val bankAccountUseCase: BankAccountUseCase
) {

    @GetMapping("/bank-accounts/{membershipId}")
    fun getBankAccounts(@PathVariable("membershipId") membershipId: Long): ResponseEntity<List<BankAccount>> {
        val command = FindBankAccountsCommand(membershipId)
        return ResponseEntity.ok(bankAccountUseCase.getBankAccounts(command))
    }

    @PostMapping("/bank-accounts")
    fun registerMembership(@RequestBody request: RegisterAccountRequest): BankAccount {
        // request -> Command (Requst 변화에 따른 Command/ UseCase 변화 최소화)
        val command = RegisterAccountCommand(
            bankAccountNumber = request.bankAccountNumber,
            membershipId = request.membershipId,
            bankName = request.bankName,
            linkedStatusIsValid = request.linkedStatusIsValid,
        )
        return bankAccountUseCase.createRegisteredBankAccount(command)?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Bank Account Info")
    }


}