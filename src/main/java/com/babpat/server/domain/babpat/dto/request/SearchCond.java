package com.babpat.server.domain.babpat.dto.request;

import java.util.List;

public record SearchCond(
        List<String> foodCond,
        List<Integer> peopleCond,
        String keywordCond
) {
}
