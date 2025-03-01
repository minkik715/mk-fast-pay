package io.github.minkik715.mkpay.money.adapter.out.persistence

import io.github.minkik715.mkpay.money.domain.MemberMoney
import io.github.minkik715.mkpay.money.domain.TargetMembershipId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SpringDataMemberMoneyRepository: JpaRepository<MemberMoneyJpaEntity, Long>{
    fun findByMembershipId(membershipId: Long): MemberMoneyJpaEntity?


    @Query("SELECT COALESCE(SUM(m.balance), 0) FROM MemberMoneyJpaEntity m WHERE m.membershipId IN :ids")
    fun findMoneySumByMembershipIds(@Param("ids") ids: List<Long>): Long

}
