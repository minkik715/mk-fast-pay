package io.github.minkik715.io.github.minkik715.membership.adapater.`in`.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
//WebAdapter
@RestController
class RegisterMembershipController {

    @GetMapping("/test")
    fun test(): String {
        return "test"
    }
}