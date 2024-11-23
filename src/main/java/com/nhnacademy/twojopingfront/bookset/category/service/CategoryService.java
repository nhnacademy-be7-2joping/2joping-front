package com.nhnacademy.twojopingfront.bookset.category.service;

import com.nhnacademy.twojopingfront.bookset.category.client.CategoryClient;
import com.nhnacademy.twojopingfront.bookset.category.dto.response.GetAllCategoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryClient categoryClient;
    public List<GetAllCategoriesResponse> getAllCategories() {
        return categoryClient.getAllCategories().getBody();
    }
}
