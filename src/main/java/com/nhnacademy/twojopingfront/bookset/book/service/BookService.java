package com.nhnacademy.twojopingfront.bookset.book.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.twojopingfront.bookset.book.client.BookClient;
import com.nhnacademy.twojopingfront.bookset.book.dto.request.BookCreateHtmlRequestDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.request.BookCreateRequestDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.request.ImageUploadRequestDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.request.ImageUrlRequestDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookAdminSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.request.*;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookCreateResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookUpdateResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.exception.FeignClientServerFailConnectionException;
import com.nhnacademy.twojopingfront.bookset.category.dto.response.CategoryResponseDto;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.response.ContributorNameRoleResponseDto;
import com.nhnacademy.twojopingfront.bookset.publisher.dto.response.PublisherResponseDto;
import com.nhnacademy.twojopingfront.bookset.tag.client.TagClient;
import com.nhnacademy.twojopingfront.bookset.tag.dto.TagResponseDto;
import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import com.nhnacademy.twojopingfront.common.error.enums.RedirectType;
import com.nhnacademy.twojopingfront.common.error.exception.image.ImageUploadException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class BookService {

    private final BookClient bookClient;
    private final TagClient tagClient;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 이미지를 저장하는 메서드
     *
     * @param image
     * @return 업로드된 이미지의 URL
     */
    public String saveImage(MultipartFile image, String type) {
        if (image == null || image.isEmpty()) {
            return null;
        }

        String uploadUrl = null;

        try {
            String folderPath = "thumbnail/";
            if ("detail".equalsIgnoreCase(type)) {
                folderPath = "detail/";
            }

            InputStream inputStream = image.getInputStream();
            String fileName = UUID.randomUUID().toString() + ".jpg";
            uploadUrl = "https://api-image.nhncloudservice.com/image/v2.0/appkeys/rUN43QEwj1P6jThk/images?path=/ejoping/book/" + folderPath + fileName;

            RequestCallback requestCallback = request -> {
                request.getHeaders().add("Content-Type", "multipart/form-data");
                request.getHeaders().add("Authorization", "I1XLVufp");
                IOUtils.copy(inputStream, request.getBody());
            };

            String responseBody = restTemplate.execute(uploadUrl, HttpMethod.PUT, requestCallback, response -> {
                InputStream body = response.getBody();
                if (body != null) {
                    return new String(body.readAllBytes(), StandardCharsets.UTF_8);
                }
                return null;
            });

            if (responseBody != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseBody);
                return rootNode.path("file").path("url").asText();
            }

        } catch (IOException e) {
            throw new ImageUploadException();
        }
        return null;
    }

    /**
     * 도서를 등록하는 메서드
     *
     * @param bookCreateHtmlRequestDto
     * @param imageUploadRequestDto
     * @return 등록된 도서에 대한 응답 정보
     */
    public BookCreateResponseDto createBook(BookCreateHtmlRequestDto bookCreateHtmlRequestDto, ImageUploadRequestDto imageUploadRequestDto) {
        String thumbnailImageUrl = saveImage(imageUploadRequestDto.thumbnailImage(), "thumbnail");
        String detailImageUrl = saveImage(imageUploadRequestDto.detailImage(), "detail");
        ImageUrlRequestDto imageUrlRequestDto = new ImageUrlRequestDto(thumbnailImageUrl, detailImageUrl);
        return bookClient.createBook(new BookCreateRequestDto(bookCreateHtmlRequestDto, imageUrlRequestDto));
    }

    /**
     * 태그 데이터를 가져오는 메서드
     * @return 모든 태그 리스트
     */
    public ResponseEntity<List<TagResponseDto>> getAllTags() {
        return tagClient.getAllTags();
    }

    /**
     * 출판사 데이터를 가져오는 메서드
     * @return 모든 출판사 리스트
     */
    public List<PublisherResponseDto> getAllPublishersForRegister() {
        return bookClient.getAllPublishersForRegister();
    }

    /**
     * 기여자명과 기여자 역할 데이터를 가져오는 메서드
     * @return 모든 기여자명과 역할 리스트
     */
    public List<ContributorNameRoleResponseDto> getActiveContributors() {
        return bookClient.getActiveContributors();
    }

    /**
     * 최상위 카테고리 데이터를 가져오는 메서드
     * @return 최상위 카테고리 리스트
     */
    public List<CategoryResponseDto> getTopCategories() {
        return bookClient.getTopCategories();
    }

    /**
     * 특정 카테고리의 자식 카테고리 데이터를 가져오는 메서드
     * @param categoryId 부모 카테고리 ID
     * @return 자식 카테고리 리스트
     */
    public List<CategoryResponseDto> getChildCategories(Long categoryId) {
        return bookClient.getChildCategories(categoryId);
    }

    /**
     * 전체 도서 목록을 가져오는 메서드
     * @return 도서 목록 페이지
     */
    public Page<BookSimpleResponseDto> getAllBooks(int page, int size) {
        return bookClient.getAllBooks(page, size);
    }

    /**
     * 관리자용 전체 도서 목록을 가져오는 메서드
     * @return 도서 목록 페이지
     */
    public Page<BookAdminSimpleResponseDto> adminGetAllBooks(int page, int size) {
        return bookClient.adminGetAllBooks(page, size);
    }

    /**
     * 카테고리별 도서 목록을 가져오는 메서드
     * @param categoryId 카테고리 ID
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
    /**
     * 도서를 수정을 위해 도서 정보를 가져오는 메서드
     *
     * @param bookId
     * @return 수정 예정 도서에 대한 응답 정보
     */
    public BookUpdateResponseDto getUpdateBookById(Long bookId) {
        return bookClient.getUpdateBookById(bookId);
    }

    /**
     * 특정 도서를 비활성화하는 메서드
     * @param bookId 비활성화할 도서 ID
     */
    public void deactivateBook(Long bookId) {
        bookClient.deactivateBook(bookId);
    }

    /**
     * 도서를 수정하는 메서드
     *
     * @param bookUpdateHtmlRequestDto
     * @param imageUploadRequestDto
     * @return 수정된 도서에 대한 응답 정보
     */
    public BookUpdateResponseDto updateBook(Long bookId, BookUpdateHtmlRequestDto bookUpdateHtmlRequestDto, ImageUploadRequestDto imageUploadRequestDto) {
        String thumbnailImageUrl = saveImage(imageUploadRequestDto.thumbnailImage(), "thumbnail");
        String detailImageUrl = saveImage(imageUploadRequestDto.detailImage(), "detail");
        ImageUrlRequestDto imageUrlRequestDto = new ImageUrlRequestDto(thumbnailImageUrl, detailImageUrl);
        return bookClient.updateBook(bookId, new BookUpdateRequestDto(bookUpdateHtmlRequestDto, imageUrlRequestDto));
    }
}