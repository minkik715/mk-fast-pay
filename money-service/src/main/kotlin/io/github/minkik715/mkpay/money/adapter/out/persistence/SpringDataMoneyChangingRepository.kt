package io.github.minkik715.mkpay.money.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataMoneyChangingRepository: JpaRepository<MoneyChangingRequestJpaEntity, Long>
