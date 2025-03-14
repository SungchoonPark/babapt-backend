package com.babpat.server.domain.settlement.repository;

import com.babpat.server.domain.settlement.entity.Settlement;
import com.babpat.server.domain.settlement.repository.custom.SettlementCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepository extends JpaRepository<Settlement, Long>, SettlementCustomRepository {
}
