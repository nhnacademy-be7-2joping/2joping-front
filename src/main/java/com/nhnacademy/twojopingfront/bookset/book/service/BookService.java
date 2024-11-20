package com.nhnacademy.twojopingfront.bookset.book.service;

import com.nhnacademy.twojopingfront.bookset.book.client.BookClient;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.exception.FeignClientServerFailConnectionException;
import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import com.nhnacademy.twojopingfront.common.error.enums.RedirectType;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;


@Component
@RequiredArgsConstructor
public class BookService {

    private final BookClient bookClient;

    /**
     * 전체 도서 목록을 가져오는 메서드
     * @return 도서 목록 페이지
     */
    public Page<BookSimpleResponseDto> getAllBooks(int page, int size) {
        return bookClient.getAllBooks(page, size);
    }

    /**
     * 카테고리별 도서 목록을 가져오는 메서드
     * @param categoryId 카테고리 ID
//     * @param pageable 페이징 정보
     * @return 해당 카테고리의 도서 목록 페이지
     */
    public Page<BookSimpleResponseDto> getBooksByCategoryId(@PathVariable Long categoryId, int page, int size) {
        return bookClient.getBooksByCategoryId(categoryId, page, size);
    }

    /**
     * 기여자별 도서 목록을 가져오는 메서드
     * @param contributorId 기여자 ID
//     * @param pageable 페이징 정보
     * @return 해당 기여자가 참여한 도서 목록 페이지
     */
    public Page<BookSimpleResponseDto> getBooksByContributorId(@PathVariable Long contributorId, int page, int size) {
        return bookClient.getBooksByContributorId(contributorId, page, size);
    }

    /**
     * 특정 도서의 상세 정보를 가져오는 메서드
     * @param bookId 조회할 도서 ID
     * @return 도서의 상세 정보
     */
    public BookResponseDto getBookById(@PathVariable Long bookId) {
        try{
        return bookClient.getBookById(bookId);
        } catch (FeignException e) {
            throw new FeignClientServerFailConnectionException(
                    new ErrorResponseDto(404,"404","해당 도서를 찾을 수 없습니다.", RedirectType.REDIRECT,"/books/search", null));
        }
    }
}

