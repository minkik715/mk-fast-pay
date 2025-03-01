package io.github.minkik715.mkpay.common.feign.money

data class GetMembershipsMoneySumFeignRequest(
    val membershipIds: Set<Long>,
)