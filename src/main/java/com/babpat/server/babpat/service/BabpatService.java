package com.babpat.server.babpat.service;

import com.babpat.server.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.babpat.entity.Babpat;
import com.babpat.server.babpat.repository.BabpatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BabpatService {
    private final BabpatRepository babpatRepository;

    public Long registerBabpat(BabpatPostReqDto babpatPostReqDto) {
        Babpat babpat = babpatRepository.save(babpatPostReqDto.toBabpat());
        return babpat.getId();
    }
}
