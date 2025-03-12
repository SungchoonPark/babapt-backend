package com.babpat.server.domain.babpat.repository.custom;

import com.babpat.server.domain.babpat.dto.request.SearchCond;
import com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BabpatCustomRepository {
    Page<BabpatInfoRespDto> getBabpats(SearchCond searchCond, Pageable pageable);
}
