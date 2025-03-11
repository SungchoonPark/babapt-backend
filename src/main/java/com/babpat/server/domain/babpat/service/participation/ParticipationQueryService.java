package com.babpat.server.domain.babpat.service.participation;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ParticipationQueryService {
    boolean isExistParticipationByDateTime(Long memberId, LocalDate patDate, LocalTime patTime);
}
