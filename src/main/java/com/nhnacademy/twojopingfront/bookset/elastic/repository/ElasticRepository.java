package com.nhnacademy.twojopingfront.bookset.elastic.repository;

import com.nhnacademy.twojopingfront.bookset.elastic.dto.response.ElasticSearchResponseDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticRepository extends ElasticsearchRepository<ElasticSearchResponseDto, String>, ElasticRepositoryCustom {
}
