package com.nhnacademy.twojopingfront.bookset.category.service;

import com.nhnacademy.twojopingfront.bookset.category.client.CategoryClient;
import com.nhnacademy.twojopingfront.bookset.category.dto.request.CategoryRequestDto;
import com.nhnacademy.twojopingfront.bookset.category.dto.response.CategoryIsActiveResponseDto;
import com.nhnacademy.twojopingfront.bookset.category.dto.response.CategoryResponseDto;
import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import com.nhnacademy.twojopingfront.common.error.enums.RedirectType;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryClient categoryClient;

    /**
     * 특정 카테고리 조회
     *
     * @param categoryId 조회할 카테고리 ID
     * @return 카테고리 DTO
     */
    public CategoryResponseDto getCategory(Long categoryId) {
        try {
            return categoryClient.getCategory(categoryId).getBody();
        } catch (Exception e) {
            throw categoriesCustomApiException("CATEGORY_FETCH_SINGLE_ERROR", "카테고리를 불러올 수 없습니다.");
        }
    }

    /**
     * 모든 카테고리 조회
     *
     * @return 카테고리 리스트
     */
    public List<CategoryResponseDto> getAllCategories() {
        try {
            return categoryClient.getAllCategories().getBody();
        } catch (Exception e) {
            throw categoriesCustomApiException("CATEGORY_FETCH_ERROR", "카테고리 목록을 불러올 수 없습니다.", "/");
        }
    }

    /**
     * 페이징된 카테고리 조회
     *
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 페이징된 카테고리 데이터
     */
    public Page<CategoryIsActiveResponseDto> getAllCategoriesPage(int page, int size) {
        try {
            return categoryClient.getAllCategoriesPage(page, size).getBody();
        } catch (Exception e) {
            throw categoriesCustomApiException("CATEGORY_FETCH_PAGE_ERROR", "카테고리 목록을 불러올 수 없습니다.");
        }
    }

    /**
     * 카테고리 생성
     *
     * @param request 카테고리 생성 요청
     */
    public void createCategory(CategoryRequestDto request) {
        try {
            categoryClient.createCategory(request);
        } catch (Exception e) {
            throw categoriesCustomApiException("CATEGORY_CREATE_ERROR", "이미 카테고리 이름이 있거나, 부모 카테고리 아이디가 존재 하지 않아, 카테고리를 생성할 수 없습니다.");
        }
    }

    /**
     * 카테고리 업데이트
     *
     * @param categoryId 업데이트할 카테고리 ID
     * @param request    카테고리 업데이트 요청
     * @return 업데이트된 카테고리 DTO
     */
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto request) {
        try {
            return categoryClient.updateCategory(categoryId, request).getBody();
        } catch (Exception e) {
            throw categoriesCustomApiException("CATEGORY_UPDATE_ERROR", "이미 카테고리 이름이 있거나, 부모 카테고리 아이디가 존재 하지 않아, 카테고리를 업데이트할 수 없습니다.");
        }
    }

    /**
     * 카테고리 비활성화
     *
     * @param categoryId 비활성화할 카테고리 ID
     */
    public void deactivateCategory(Long categoryId) {
        try {
            categoryClient.deactivateCategory(categoryId);
        } catch (Exception e) {
            throw categoriesCustomApiException("CATEGORY_DEACTIVATE_ERROR", "하위 카테고리가 존재하여, 해당 카테고리를 비활성화할 수 없습니다.");
        }
    }

    /**
     * 카테고리 활성화
     *
     * @param categoryId 활성화할 카테고리 ID
     */
    public void activateCategory(Long categoryId) {
        try {
            categoryClient.deactivateCategory(categoryId);
        } catch (Exception e) {
            throw categoriesCustomApiException("CATEGORY_ACTIVATE_ERROR", "해당 카테고리를 활성화할 수 없습니다.");
        }
    }

    /**
     * CustomApiException 생성 메서드
     *
     * @param errorCode    사용자 정의 오류 코드
     * @param errorMessage 오류 메시지
     * @return 생성된 CustomApiException
     */
    private CustomApiException categoriesCustomApiException(String errorCode, String errorMessage, String url) {
        ErrorResponseDto<?> errorResponse = new ErrorResponseDto<>(
                500,
                errorCode,
                errorMessage,
                RedirectType.REDIRECT,
                url,
                null
        );
        return new CustomApiException(errorResponse);
    }

    private CustomApiException categoriesCustomApiException(String errorCode, String errorMessage) {
        return categoriesCustomApiException(errorCode, errorMessage, "/admin/categories");
    }
}
