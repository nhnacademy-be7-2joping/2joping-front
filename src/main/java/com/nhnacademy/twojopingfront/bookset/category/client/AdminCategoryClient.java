package com.nhnacademy.twojopingfront.bookset.category.client;

import com.nhnacademy.twojopingfront.bookset.category.dto.response.CategoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "AdminCategoryClient", url = "${gateway.base-url}")
public interface AdminCategoryClient {

    @GetMapping("/v1/bookstore/categories")
    ResponseEntity<List<CategoryResponseDto>> getAllCategories();


    // TODO: 관리자용 카테고리 API 쏘기
}
