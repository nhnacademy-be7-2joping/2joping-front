package com.nhnacademy.twojopingfront.bookset.elastic.repository.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.nhnacademy.twojopingfront.bookset.elastic.dto.response.ElasticSearchResponseDto;
import com.nhnacademy.twojopingfront.bookset.elastic.repository.ElasticRepositoryCustom;
import com.nhnacademy.twojopingfront.common.config.ElasticConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ElasticRepositoryImpl implements ElasticRepositoryCustom {

    private final ElasticsearchClient elasticsearchClient;
    private final ElasticConfig elasticConfig;

    @Override
    public Page<ElasticSearchResponseDto> searchByKeyword(String keyword, Pageable pageable, String sort) {
        try {
            // 정렬 기준 설정
            List<Map.Entry<String, String>> sortCriteria = switch (sort) {
                case "popularity" -> List.of(Map.entry("book_likes", "desc"), Map.entry("book_views", "desc"));
                case "newest" -> List.of(Map.entry("book_published_at", "desc"));
                case "lowest_price" -> List.of(Map.entry("book_selling_price", "asc"));
                case "highest_price" -> List.of(Map.entry("book_selling_price", "desc"));
                case "rating" -> List.of(Map.entry("book_rating_avg", "desc"));
                case "reviews" -> List.of(Map.entry("book_review_count", "desc"));
                default -> List.of(); // 기본 정렬 (관련성)
            };

            // Elasticsearch 검색 요청 생성
            SearchRequest.Builder requestBuilder = new SearchRequest.Builder()
                    .index(elasticConfig.getBookIndexName())
                    .from((int) pageable.getOffset())
                    .size(pageable.getPageSize());

            // 정렬 기준 추가
            for (Map.Entry<String, String> entry : sortCriteria) {
                requestBuilder.sort(s -> s
                        .field(f -> f
                                .field(entry.getKey())
                                .order(entry.getValue().equals("desc") ? co.elastic.clients.elasticsearch._types.SortOrder.Desc : co.elastic.clients.elasticsearch._types.SortOrder.Asc)
                        ));
            }

            // 검색어가 있으면 키워드 기반 검색, 없으면 match_all
            if (keyword == null || keyword.isBlank()) {
                requestBuilder.query(q -> q.matchAll(m -> m));
            } else {
                requestBuilder.query(q -> q.bool(b -> b
                        .should(s1 -> s1.multiMatch(m -> m
                                .fields("book_title^10", "book_title.ngram^3", "book_title.jaso",
                                        "book_desc^3", "book_desc.ngram", "book_desc.raw",
                                        "book_publisher^2", "book_publisher.ngram", "book_publisher.raw")
                                .query(keyword)))
                        .should(s1 -> s1.nested(n -> n
                                .path("book_category")
                                .query(q1 -> q1.multiMatch(m -> m
                                        .fields("book_category.name^3", "book_category.name.ngram", "book_category.name.raw")
                                        .query(keyword)))))
                        .should(s1 -> s1.nested(n -> n
                                .path("book_tag")
                                .query(q1 -> q1.multiMatch(m -> m
                                        .fields("book_tag.name^3", "book_tag.name.ngram", "book_tag.name.raw")
                                        .query(keyword)))))
                        .should(s1 -> s1.nested(n -> n
                                .path("book_contributor")
                                .query(q1 -> q1.multiMatch(m -> m
                                        .fields("book_contributor.name^2", "book_contributor.name.ngram", "book_contributor.name.raw")
                                        .query(keyword)))))
                ));
            }

            // Elasticsearch 호출
            SearchResponse<ElasticSearchResponseDto> response = elasticsearchClient.search(requestBuilder.build(), ElasticSearchResponseDto.class);

            // 결과 처리
            List<ElasticSearchResponseDto> results = response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());

            return new PageImpl<>(results, pageable, response.hits().total().value());

        } catch (IOException e) {
            throw new RuntimeException("Failed to execute Elasticsearch query", e);
        }
    }
}

