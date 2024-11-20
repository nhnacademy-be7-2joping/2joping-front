package com.nhnacademy.twojopingfront.bookset.elastic.controller;

import com.nhnacademy.twojopingfront.bookset.elastic.dto.response.ElasticSearchResponseDto;
import com.nhnacademy.twojopingfront.bookset.elastic.service.ElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ElasticController {

    private final ElasticService elasticService;

    @GetMapping("/books/search")
    public String searchBooks(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "relevance") String sort,
            Model model) {

        // 검색 결과 가져오기
        Page<ElasticSearchResponseDto> books = elasticService.searchBooks(keyword, PageRequest.of(page, size), sort);

        // 모델에 검색 결과와 페이징 정보 추가
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("currentPath", "/books/search");

        return "bookset/search/search-list";
    }
}
