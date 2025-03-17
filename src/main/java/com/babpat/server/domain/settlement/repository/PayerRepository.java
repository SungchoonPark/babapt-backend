package com.babpat.server.domain.settlement.repository;

import com.babpat.server.domain.settlement.entity.Payer;
import com.babpat.server.domain.settlement.repository.custom.PayerCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayerRepository extends JpaRepository<Payer, Long>, PayerCustomRepository {
}
