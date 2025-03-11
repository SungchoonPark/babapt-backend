package com.babpat.server.domain.babpat.service.babpat;

import com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.domain.babpat.entity.Babpat;

public interface BabpatQueryService {
    BabpatInfoRespDto getBabpat();

    Babpat getBabpatDetail(Long babpatId);
}
