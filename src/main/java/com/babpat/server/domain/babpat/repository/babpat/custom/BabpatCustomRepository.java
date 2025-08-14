package com.babpat.server.domain.babpat.repository.babpat.custom;

import com.babpat.server.domain.babpat.dto.request.SearchCond;
import com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.domain.babpat.dto.response.PartBabpatId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BabpatCustomRepository {

  Page<BabpatInfoRespDto> getBabpats(SearchCond searchCond, Pageable pageable);

  Page<BabpatInfoRespDto> getMemberParticipationBabpats(String authUsername, Pageable pageable);

  PartBabpatId getParticipatingBabpat(String authUsername);

}
