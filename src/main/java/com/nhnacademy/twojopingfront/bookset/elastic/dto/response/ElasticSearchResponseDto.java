package com.nhnacademy.twojopingfront.bookset.elastic.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import java.util.List;

@Document(indexName = "ejoping-books")
@JsonIgnoreProperties(ignoreUnknown = true)
public record ElasticSearchResponseDto (
        @Id
        @JsonProperty("_id")
        String bookIsbn,
        @JsonProperty("book_id")
        Long bookId,
        @JsonProperty("book_title")
        String bookTitle,
        @JsonProperty("book_contributor")
        List<ElasticContributor> bookContributor,
        @JsonProperty("book_tag")
        List<ElasticTag> bookTag,
        @JsonProperty("book_category")
        List<ElasticCategory> bookCategory,
        @JsonProperty("book_publisher")
        String bookPublisher,
        @JsonProperty("book_desc")
        String bookDesc,
        @JsonProperty("book_retail_price")
        Integer bookRetailPrice,
        @JsonProperty("book_selling_price")
        Integer bookSellingPrice,
        @JsonProperty("book_published_at")
        String bookPublishedAt,
        @JsonProperty("book_views")
        Integer bookViews,
        @JsonProperty("book_likes")
        Integer bookLikes,
        @JsonProperty("book_thumbnail_url")
        String thumbnail,
        @JsonProperty("book_review_count")
        Integer bookReviewCount,
        @JsonProperty("book_rating_avg")
        Double bookRatingAvg
) {
    public record ElasticContributor(
            @JsonProperty("id")
            Long id,
            @JsonProperty("name")
            String name,
            @JsonProperty("role")
            String role) {
    }

    public record ElasticTag(
            @JsonProperty("id")
            Long id,
            @JsonProperty("name")
            String name) {
    }

    public record ElasticCategory(
            @JsonProperty("id")
            Long id,
            @JsonProperty("name")
            String name) {
    }
}

