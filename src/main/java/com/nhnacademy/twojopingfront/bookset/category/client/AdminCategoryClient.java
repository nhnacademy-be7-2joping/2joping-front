package com.nhnacademy.twojopingfront.bookset.category.client;

import com.nhnacademy.twojopingfront.bookset.category.dto.response.GetAllCategoriesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "CategoryClient", url = "${gateway.base-url}")
public interface AdminCategoryClient {

    @GetMapping("/v1/bookstore/categories")
    ResponseEntity<List<GetAllCategoriesResponse>> getAllCategories();
}
