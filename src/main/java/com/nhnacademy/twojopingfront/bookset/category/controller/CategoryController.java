package com.nhnacademy.twojopingfront.bookset.category.controller;

import com.nhnacademy.twojopingfront.bookset.category.dto.request.CategoryRequestDto;
import com.nhnacademy.twojopingfront.bookset.category.dto.response.CategoryResponseDto;
import com.nhnacademy.twojopingfront.bookset.category.service.CategoryService;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 목록 조회
     *
     * @param model 뷰에 데이터 전달
     * @return 카테고리 목록 페이지
     */
    @GetMapping
    public String getAllCategoriesPage(@PageableDefault(size = 10, sort = "categoryId") Pageable pageable, Model model) {
        try {
            Page<CategoryResponseDto> categories = categoryService.getAllCategoriesPage(pageable.getPageNumber(), pageable.getPageSize());
            model.addAttribute("categories", categories.getContent());
            model.addAttribute("page", categories);
        } catch (CustomApiException ex) {
            Page<CategoryResponseDto> categories = categoryService.getAllCategoriesPage(pageable.getPageNumber(), pageable.getPageSize());
            model.addAttribute("errorMessage", ex.getErrorResponse().errorMessage());
            model.addAttribute("categories", categories.getContent());
            model.addAttribute("page", categories);
            return  "admin/categories/category-list";
        }
        return "admin/categories/category-list";
    }

    /**
     * 카테고리 생성 폼 페이지로 이동
     *
     * @param model 뷰에 빈 DTO 전달
     * @return 카테고리 생성 페이지
     */
    @GetMapping("/create")
    public String showCreateCategoryForm(Model model) {
        try {
            model.addAttribute("category", new CategoryRequestDto(null, null, ""));
        } catch (CustomApiException ex) {
            model.addAttribute("errorMessage", ex.getErrorResponse().errorMessage());
            model.addAttribute("category", new CategoryRequestDto(null, null, ""));
            return "admin/categories/category-form";
        }
        return "admin/categories/category-form";


    }

    /**
     * 카테고리 수정 폼 페이지로 이동
     *
     * @param categoryId 수정할 카테고리 ID
     * @param model 뷰에 데이터 전달
     * @return 카테고리 수정 페이지
     */
    @GetMapping("/{categoryId}/edit")
    public String showEditCategoryForm(@PathVariable Long categoryId, Model model) {
        try {
            CategoryResponseDto category = categoryService.getCategory(categoryId);
            model.addAttribute("category", category);
        } catch (CustomApiException ex) {
            CategoryResponseDto category = categoryService.getCategory(categoryId);
            model.addAttribute("errorMessage", ex.getErrorResponse().errorMessage());
            model.addAttribute("category", category);
            return "admin/categories/category-form";
        }
        return "admin/categories/category-form";
    }

    /**
     * 새로운 카테고리 생성 처리
     *
     * @param request 카테고리 생성 요청 DTO
     * @return 카테고리 목록 페이지로 리다이렉트
     */
    @PostMapping
    public String createCategory(@ModelAttribute CategoryRequestDto request, RedirectAttributes redirectAttributes, Model model) {
        try {
            categoryService.createCategory(request);
            redirectAttributes.addFlashAttribute("message", "카테고리가 성공적으로 생성되었습니다.");
        } catch (CustomApiException ex) {
            model.addAttribute("errorMessage", ex.getErrorResponse().errorMessage());
            model.addAttribute("category", request);
            return "admin/categories/category-form";
        }
        return "redirect:/admin/categories";
    }

    /**
     * 카테고리 수정 처리
     *
     * @param categoryId 수정할 카테고리 ID
     * @param request    카테고리 수정 요청 DTO
     * @return 카테고리 목록 페이지로 리다이렉트
     */
    @PostMapping("/{categoryId}/edit")
    public String updateCategory(@PathVariable Long categoryId, @ModelAttribute CategoryRequestDto request,
                                 RedirectAttributes redirectAttributes, Model model) {
        try {
            categoryService.updateCategory(categoryId, request);
            redirectAttributes.addFlashAttribute("message", "카테고리가 성공적으로 수정되었습니다.");
        } catch (CustomApiException ex) {
            model.addAttribute("errorMessage", ex.getErrorResponse().errorMessage());
            model.addAttribute("category", request);
            return "admin/categories/category-form";
        }
        return "redirect:/admin/categories";
    }

    /**
     * 카테고리 비활성화 처리
     *
     * @param categoryId 비활성화할 카테고리 ID
     * @return 카테고리 목록 페이지로 리다이렉트
     */
    @PostMapping("/{categoryId}/deactivate")
    public String deactivateCategory(@PathVariable Long categoryId, RedirectAttributes redirectAttributes, Model model) {
        try {
            categoryService.deactivateCategory(categoryId);
            redirectAttributes.addFlashAttribute("message", "카테고리가 성공적으로 비활성화 되었습니다.");
        } catch (CustomApiException ex) {
            model.addAttribute("errorMessage", ex.getErrorResponse().errorMessage());
            return "admin/category/categories-list";
        }
        return "redirect:/admin/categories";
    }
}
