package com.babpat.server.domain.babpat.repository.babpat;

import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.babpat.repository.babpat.custom.BabpatCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BabpatRepository extends JpaRepository<Babpat, Long>, BabpatCustomRepository {
}
