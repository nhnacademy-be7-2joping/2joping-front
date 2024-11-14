package com.nhnacademy.twojopingfront.bookset.book.controller;

import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookContributorResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookTagResponseDto;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
@TestPropertySource(locations = "classpath:properties/gateway.properties")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private BookSimpleResponseDto bookSimpleDto;
    private Page<BookSimpleResponseDto> bookPage;
    private BookResponseDto bookResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookSimpleDto = new BookSimpleResponseDto(
                1L, "Thumbnail", "Title", 10000, "Publisher", 12000,
                List.of(new BookContributorResponseDto(1L, "Contributor 1", 1L, "Author")),
                List.of("Category 1", "Category 2")
        );
        bookPage = new PageImpl<>(List.of(bookSimpleDto));

        bookResponse = new BookResponseDto(
                1L, "Publisher", "Title", "Description", LocalDate.now(), "1234567890123",
                10000, 9000, true, true, 10, 0, 0,
                List.of(new BookContributorResponseDto(1L, "Contributor 1", 1L, "Author")),
                List.of("Category 1", "Category 2"),List.of(new BookTagResponseDto(1L,"Tag 1")), "thumbnail"
        );
    }

    @Test
    @DisplayName("전체 도서 목록 조회 테스트")
    void getAllBooks() throws Exception {
        when(bookService.getAllBooks(0, 10)).thenReturn(bookPage);

        mockMvc.perform(get("/books/get"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookset/book/get-booklist"))
                .andExpect(model().attribute("books", bookPage))
                .andExpect(model().attribute("currentPath", "/books/get"));
    }

    @Test
    @DisplayName("카테고리별 도서 목록 조회 테스트")
    void getBooksByCategoryId() throws Exception {
        when(bookService.getBooksByCategoryId(anyLong(), anyInt(), anyInt())).thenReturn(bookPage);

        mockMvc.perform(get("/books/get/category/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookset/book/get-booklist"))
                .andExpect(model().attribute("books", bookPage))
                .andExpect(model().attribute("currentPath", "/books/get/category/1"));
    }

    @Test
    @DisplayName("기여자별 도서 목록 조회 테스트")
    void getBooksByContributorId() throws Exception {
        when(bookService.getBooksByContributorId(anyLong(), anyInt(), anyInt())).thenReturn(bookPage);

        mockMvc.perform(get("/books/get/contributor/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookset/book/get-booklist"))
                .andExpect(model().attribute("books", bookPage))
                .andExpect(model().attribute("currentPath", "/books/get/contributor/1"));
    }

    @Test
    @DisplayName("도서 상세 조회 테스트")
    void getBookByBookId() throws Exception {
        when(bookService.getBookById(anyLong())).thenReturn(bookResponse);

        mockMvc.perform(get("/books/get/book/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookset/book/bookdetails"))
                .andExpect(model().attribute("books", bookResponse));
    }
}