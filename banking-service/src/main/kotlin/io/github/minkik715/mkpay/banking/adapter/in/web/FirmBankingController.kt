package io.github.minkik715.mkpay.banking.adapter.`in`.web

import io.github.minkik715.mkpay.common.WebAdapter
import io.github.minkik715.mkpay.banking.application.port.`in`.*
import io.github.minkik715.mkpay.banking.domain.FirmbankingRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@WebAdapter
@RestController
class FirmbankingController(
    private val firmbankingUseCase: FirmbankingUseCase
) {

    /*//@GetMapping("/bank-accounts/{membershipId}")
    fun getMembershipByMemberId(@PathVariable("membershipId") membershipId: Long): ResponseEntity<List<BankAccount>> {
        val command = FindBankAccountsCommand(membershipId)
        return ResponseEntity.ok(bankAccountUseCase.getBankAccounts(command))
    }*/

    @PostMapping("/firmbanking")
    fun requestFirmbakning(@RequestBody request: RequestFirmbankingRequest): FirmbankingRequest {
        // request -> Command (Requst 변화에 따른 Command/ UseCase 변화 최소화)
        val command = FirmbankingRequestCommand(
            fromBankName = request.fromBankName,
            fromBankAccountNumber = request.fromBankAccountNumber,
            toBankName = request.toBankName,
            toBankAccountNumber = request.toBankAccountNumber,
            moneyAmount = request.moneyAmount,
        )
        return firmbankingUseCase.requestFirmbanking(command)?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Bank Account Info")
    }


}