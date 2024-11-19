package com.nhnacademy.twojopingfront.bookset.elastic.service;

import com.nhnacademy.twojopingfront.bookset.elastic.dto.response.ElasticSearchResponseDto;
import com.nhnacademy.twojopingfront.bookset.elastic.repository.ElasticRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElasticService {

    private final ElasticRepositoryCustom elasticRepository;

    public Page<ElasticSearchResponseDto> searchBooks(String keyword, Pageable pageable, String sort) {
        return elasticRepository.searchByKeyword(keyword, pageable, sort);
    }
}
