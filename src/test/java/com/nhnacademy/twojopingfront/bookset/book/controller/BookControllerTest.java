package com.nhnacademy.twojopingfront.bookset.book.controller;

import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("전체 도서 목록 조회 테스트")
    void getAllBooks() throws Exception {
        Page<BookSimpleResponseDto> bookPage = new PageImpl<>(List.of(new BookSimpleResponseDto(1L, "Title", "Thumbnail", 10000, "Publisher", 12000,List.of("Contributor 1", "Contributor 2"),List.of("Category 1", "Category 2"))));
        when(bookService.getAllBooks(0, 2)).thenReturn(bookPage);

        mockMvc.perform(get("/bookstore/books/get"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookset/get-bookList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("currentPath", "/bookstore/books/get"));
    }

    @Test
    @DisplayName("카테고리별 도서 목록 조회 테스트")
    void getBooksByCategoryId() throws Exception {
        Page<BookSimpleResponseDto> bookPage = new PageImpl<>(List.of(new BookSimpleResponseDto(1L, "Title", "Thumbnail", 10000, "Publisher", 12000,List.of("Contributor 1", "Contributor 2"),List.of("Category 1", "Category 2"))));
        when(bookService.getBooksByCategoryId(anyLong(), any(int.class), any(int.class))).thenReturn(bookPage);

        mockMvc.perform(get("/bookstore/books/get/category/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookset/get-bookList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("currentPath", "/bookstore/books/get/category/1"));
    }

    @Test
    @DisplayName("기여자별 도서 목록 조회 테스트")
    void getBooksByContributorId() throws Exception {
        Page<BookSimpleResponseDto> bookPage = new PageImpl<>(List.of(new BookSimpleResponseDto(1L, "Title", "Thumbnail", 10000, "Publisher", 12000,List.of("Contributor 1", "Contributor 2"),List.of("Category 1", "Category 2"))));
        when(bookService.getBooksByContributorId(anyLong(), any(int.class), any(int.class))).thenReturn(bookPage);

        mockMvc.perform(get("/bookstore/books/get/contributor/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookset/get-bookList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("currentPath", "/bookstore/books/get/contributor/1"));
    }

    @Test
    @DisplayName("도서 상세 조회 테스트")
    void getBookByBookId() throws Exception {
        BookResponseDto bookResponse = new BookResponseDto(1L, "Title", "Description", "2023-10-01", LocalDate.now(), "1234567890123", 10000, 9000, true, true, 10, 0,0,List.of("Contributor 1", "Contributor 2"),List.of("Category 1", "Category 2"),"thumbnail");
        when(bookService.getBookById(anyLong())).thenReturn(bookResponse);

        mockMvc.perform(get("/bookstore/books/get/book/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookset/bookdetails"))
                .andExpect(model().attributeExists("books"));
    }
}
