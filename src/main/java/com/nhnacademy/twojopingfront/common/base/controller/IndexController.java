package com.nhnacademy.twojopingfront.common.base.controller;

import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @CookieValue(name = "accessToken", defaultValue = "") String accessToken,
                        Model model) {
        Page<BookSimpleResponseDto> books = bookService.getAllBooks(page, size);
        model.addAttribute("books", books);
        model.addAttribute("token", accessToken);

        return "common/index";
    }
}
