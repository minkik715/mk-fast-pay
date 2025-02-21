package io.github.minkik715.mkpay.membership.adapater.`in`.web

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.minkik715.membership.domain.*
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
class RegisterMembershipControllerTest{


    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper
    @Test
    fun testRegisterMembership(){
        val request = io.github.minkik715.mkpay.membership.adapater.`in`.web.RegisterMembershipRequest(
            "name",
            "address",
            "email",
            false
        )

        val membershipExpect = io.github.minkik715.mkpay.membership.domain.Membership.generateMember(
            io.github.minkik715.mkpay.membership.domain.MembershipId(1),
            io.github.minkik715.mkpay.membership.domain.MembershipName(request.name),
            io.github.minkik715.mkpay.membership.domain.MembershipEmail(request.email),
            io.github.minkik715.mkpay.membership.domain.MembershipAddress(request.address),
            io.github.minkik715.mkpay.membership.domain.MembershipIsValid(true),
            io.github.minkik715.mkpay.membership.domain.MembershipIsCorp(request.isCorp)
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/memberships")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(membershipExpect)))
    }

}