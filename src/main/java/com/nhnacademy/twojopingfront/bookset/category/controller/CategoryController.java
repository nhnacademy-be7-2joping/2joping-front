package com.nhnacademy.twojopingfront.bookset.category.controller;

import com.nhnacademy.twojopingfront.bookset.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryConroller {

    private final CategoryService categoryService;


}
