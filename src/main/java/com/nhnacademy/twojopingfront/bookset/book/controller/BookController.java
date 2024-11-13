package com.nhnacademy.twojopingfront.bookset.book.controller;

import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping()
@RequiredArgsConstructor
public class    BookController {

    private final BookService bookService;

    @GetMapping("/books/get")
    public String getAllBooks(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "2") int size,
                              Model model) {
        Page<BookSimpleResponseDto> books = bookService.getAllBooks(page, size);
        model.addAttribute("books", books);
        model.addAttribute("currentPath", "/books/get");
        return "bookset/book/get-bookList";
    }

    @GetMapping("/books/get/category/{categoryId}")
    public String getBooksByCategoryId(@PathVariable Long categoryId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "2") int size,
                                       Model model) {
        Page<BookSimpleResponseDto> books = bookService.getBooksByCategoryId(categoryId, page, size);
        model.addAttribute("books", books);
        model.addAttribute("currentPath", "/books/get/category/" + categoryId);
        return "bookset/book/get-bookList";
    }

    @GetMapping("/books/get/contributor/{contributorId}")
    public String getBooksByContributorId(@PathVariable Long contributorId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "2") int size,
                                       Model model) {
        Page<BookSimpleResponseDto> books = bookService.getBooksByContributorId(contributorId, page, size);
        model.addAttribute("books", books);
        model.addAttribute("currentPath", "/books/get/contributor/" + contributorId);
        return "bookset/book/get-bookList";
    }

    @GetMapping("/books/get/book/{bookId}")
    public String getBookByBookId(@PathVariable Long bookId, Model model) {
        BookResponseDto books = bookService.getBookById(bookId);
        model.addAttribute("books", books);
        return "bookset/book/bookdetails";
    }
}
