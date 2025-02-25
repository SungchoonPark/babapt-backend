package com.babpat.server.babpat.repository;

import com.babpat.server.babpat.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    @Query("""
        SELECT COUNT(p) > 0 
        FROM Participation p
        JOIN Babpat b ON p.babpatId = b.id
        WHERE p.memberId = :memberId
        AND b.patDate = :patDate
        AND b.patTime >= :startTime
        AND b.patTime <= :endTime
    """)
    boolean existsParticipationWithinOneHour(
            @Param("memberId") Long memberId,
            @Param("patDate") LocalDate patDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
