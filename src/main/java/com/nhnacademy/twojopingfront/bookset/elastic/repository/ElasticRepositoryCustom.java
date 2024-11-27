package com.nhnacademy.twojopingfront.bookset.elastic.repository;

import com.nhnacademy.twojopingfront.bookset.elastic.dto.response.ElasticSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ElasticRepositoryCustom {
    Page<ElasticSearchResponseDto> searchByKeyword(String keyword, Long categoryId, Pageable pageable, String sort);
}
