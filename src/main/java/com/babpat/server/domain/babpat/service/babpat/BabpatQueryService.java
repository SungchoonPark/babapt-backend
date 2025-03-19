package com.babpat.server.domain.babpat.service.babpat;

import com.babpat.server.domain.babpat.dto.request.SearchCond;
import com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.domain.babpat.dto.response.PartBabpatId;
import com.babpat.server.domain.babpat.entity.Babpat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BabpatQueryService {
    Babpat getBabpatDetail(Long babpatId);

    Page<BabpatInfoRespDto> getBabpatWithPaging(SearchCond searchCond, Pageable pageable);

    PartBabpatId getParticipatingBabpats(String authUsername);
}
