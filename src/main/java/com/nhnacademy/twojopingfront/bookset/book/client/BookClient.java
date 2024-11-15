package com.nhnacademy.twojopingfront.bookset.book.client;


import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookSimpleResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bookstore", url = "${url}")
public interface BookClient {

    /**
     * 전체 도서 목록 조회
     * @param page 페이지 번호
     * @param size 페이징 크기 (몇 개를 한 페이지에 넣을지)
     * @return 도서 목록의 페이지 정보
     */
    @GetMapping("/books/get")
    Page<BookSimpleResponseDto> getAllBooks(@RequestParam("page") int page, @RequestParam("size") int size);


    /**
     * 카테고리별 도서 조회
     * @param categoryId 카테고리 ID
     * @param page 페이지 번호
     * @param size 페이징 크기 (몇 개를 한 페이지에 넣을지)
     * @return 해당 카테고리의 도서 목록 페이지
     */
    @GetMapping("/books/get/category/{categoryId}")
    Page<BookSimpleResponseDto> getBooksByCategoryId(@PathVariable Long categoryId, @RequestParam("page") int page, @RequestParam("size") int size);

    /**
     * 기여자별 도서 조회
     * @param contributorId 기여자 ID
     * @param page 페이지 번호
     * @param size 페이징 크기 (몇 개를 한 페이지에 넣을지)
     * @return 해당 기여자가 참여한 도서 목록 페이지
     */
    @GetMapping("/books/get/contributor/{contributorId}")
    Page<BookSimpleResponseDto> getBooksByContributorId(@PathVariable Long contributorId, @RequestParam("page") int page, @RequestParam("size") int size);

    /**
     * 특정 도서 상세 조회
     * @param bookId 조회할 도서 ID
     * @return 도서의 상세 정보
     */
    @GetMapping("/books/get/book/{bookId}")
    BookResponseDto getBookById(@PathVariable("bookId") Long bookId);
}


