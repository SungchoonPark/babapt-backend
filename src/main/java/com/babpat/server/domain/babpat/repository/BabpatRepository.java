package com.babpat.server.domain.babpat.repository;

import com.babpat.server.domain.babpat.entity.Babpat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BabpatRepository extends JpaRepository<Babpat, Long> {
}
