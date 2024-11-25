package com.nhnacademy.twojopingfront.bookset.book.client;


import com.nhnacademy.twojopingfront.bookset.book.dto.request.BookCreateRequestDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookAdminSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookCreateResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.category.dto.response.CategoryResponseDto;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.response.ContributorNameRoleResponseDto;
import com.nhnacademy.twojopingfront.bookset.publisher.dto.response.PublisherResponseDto;
import com.nhnacademy.twojopingfront.bookset.tag.dto.TagResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bookstore", url = "${url}")
public interface BookClient {

    /**
     * 도서 등록
     * @param bookCreateRequestDto 도서 등록 요청 정보
     * @return 등록된 도서에 대한 응답 정보
     */
    @PostMapping(value = "/admin/books/register")
    BookCreateResponseDto createBook(@RequestBody BookCreateRequestDto bookCreateRequestDto);

    /**
     * 태그 데이터를 가져오는 메서드
     * @return 모든 태그 리스트
     */
    @GetMapping("/tags")
    List<TagResponseDto> getAllTags();

    /**
     * 출판사 데이터를 가져오는 메서드
     * @return 모든 출판사 리스트
     */
    @GetMapping("/publishers/list")
    List<PublisherResponseDto> getAllPublishersForRegister();

    /**
     * 기여자 데이터를 가져오는 메서드
     * @return 모든 기여자와 역할 리스트
     */
    @GetMapping("/contributors/active")
    List<ContributorNameRoleResponseDto> getActiveContributors();

    /**
     * 최상위 카테고리 데이터를 가져오는 메서드
     * @return 최상위 카테고리 리스트
     */
    @GetMapping("/categories/top")
    List<CategoryResponseDto> getTopCategories();

    /**
     * 특정 카테고리의 자식 카테고리 데이터를 가져오는 메서드
     * @param categoryId 부모 카테고리 ID
     * @return 자식 카테고리 리스트
     */
    @GetMapping("/categories/{categoryId}/children")
    List<CategoryResponseDto> getChildCategories(@PathVariable("categoryId") Long categoryId);

    /**
     * 전체 도서 목록 조회
     * @param page 페이지 번호
     * @param size 페이징 크기 (몇 개를 한 페이지에 넣을지)
     * @return 도서 목록의 페이지 정보
     */
    @GetMapping("/books/get")
    Page<BookSimpleResponseDto> getAllBooks(@RequestParam("page") int page, @RequestParam("size") int size);

    @GetMapping("/books/get")
    Page<BookAdminSimpleResponseDto> adminGetAllBooks(@RequestParam("page") int page, @RequestParam("size") int size);


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


