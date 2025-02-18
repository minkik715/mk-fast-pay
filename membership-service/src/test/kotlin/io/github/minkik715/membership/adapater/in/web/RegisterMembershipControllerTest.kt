package io.github.minkik715.membership.adapater.`in`.web

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
        val request = RegisterMembershipRequest("name", "address","email",false )

        val membershipExpect = Membership.generateMember(
            MembershipId(1),
            MembershipName(request.name),
            MembershipEmail(request.email),
            MembershipAddress(request.address),
            MembershipIsValid(true),
            MembershipIsCorp(request.isCorp)
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/memberships/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(membershipExpect)))
    }

}