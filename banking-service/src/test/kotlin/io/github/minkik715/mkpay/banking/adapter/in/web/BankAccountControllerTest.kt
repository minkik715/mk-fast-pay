package io.github.minkik715.mkpay.banking.adapter.`in`.web

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.minkik715.mkpay.banking.domain.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class BankAccountControllerTest{
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper
    @Test
    fun testRegisterAccountBank(){
        val request = RegisterAccountRequest(
            1,
            "국민은행",
            "1242345234324",
            true
        )

        val bankAccountExpect = BankAccount.generateBankAccount(
            BankAccountId(1L),
            MembershipId(request.membershipId),
            BankName(request.bankName),
            BankAccountNumber(request.bankAccountNumber),
            LinkedStatusIstValid(request.linkedStatusIsValid)
        )


        mockMvc.perform(
            MockMvcRequestBuilders.post("/bank-accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(bankAccountExpect)))
    }

}