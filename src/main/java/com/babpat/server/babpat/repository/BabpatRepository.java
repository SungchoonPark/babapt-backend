package com.babpat.server.babpat.repository;

import com.babpat.server.babpat.entity.Babpat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BabpatRepository extends JpaRepository<Babpat, Long> {
}
