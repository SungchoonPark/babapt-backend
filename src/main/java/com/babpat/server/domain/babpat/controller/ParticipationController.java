package com.babpat.server.domain.babpat.controller;

import com.babpat.server.domain.babpat.service.participation.ParticipationCommandService;
import com.babpat.server.domain.babpat.service.participation.ParticipationQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ParticipationController {
    private final ParticipationCommandService participationCommandService;
    private final ParticipationQueryService participationQueryService;

}
