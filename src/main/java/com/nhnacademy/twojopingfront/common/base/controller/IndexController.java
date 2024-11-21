package com.nhnacademy.twojopingfront.common.base.controller;

import com.nhnacademy.twojopingfront.bookset.book.service.BookService;
import com.nhnacademy.twojopingfront.bookset.elastic.dto.response.ElasticSearchResponseDto;
import com.nhnacademy.twojopingfront.bookset.elastic.service.ElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexController {
    private final BookService bookService;
    private final ElasticService elasticService;

    @GetMapping
    public String index(@RequestParam(name = "categoryId", required = false) Long categoryId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "12") int size,
                        @RequestParam(name = "sort", required = false, defaultValue = "relevance") String sort,
                        @CookieValue(name = "accessToken", defaultValue = "") String accessToken,
                        Model model) {
        Page<ElasticSearchResponseDto> books = elasticService.searchBooks("", categoryId, PageRequest.of(page, size), sort);

        // 카테고리 순서를 역순으로 변경
        books.getContent().forEach(ElasticSearchResponseDto::getReversedBookCategory);

        model.addAttribute("books", books);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", sort);
        model.addAttribute("token", accessToken);

        return "common/index";
    }
}
