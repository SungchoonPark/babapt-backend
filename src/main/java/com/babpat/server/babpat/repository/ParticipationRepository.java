package com.babpat.server.babpat.repository;

import com.babpat.server.babpat.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}
