package com.nhnacademy.twojopingfront.admin.couponPolicy.controller;

import com.nhnacademy.twojopingfront.admin.couponPolicy.dto.CouponPolicyResponseDto;
import com.nhnacademy.twojopingfront.admin.couponPolicy.dto.CreateCouponPolicyRequest;
import com.nhnacademy.twojopingfront.admin.couponPolicy.dto.UpdateCouponPolicyRequest;
import com.nhnacademy.twojopingfront.admin.couponPolicy.service.CouponPolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Coupon Policy Controller
 * 쿠폰 정책 관리 컨트롤러
 *
 * @author
 * @date 2024-11-25
 */
@Controller
@RequestMapping("/admin/coupon/policies")
@RequiredArgsConstructor
public class CouponPolicyController {
    private final CouponPolicyService couponPolicyService;

    /**
     * 모든 쿠폰 정책 조회
     *
     * @param model 뷰로 데이터를 전달하기 위한 모델
     * @return 쿠폰 정책 목록 페이지
     */
    @GetMapping
    public String getAllCouponPolicies(Model model) {
        List<CouponPolicyResponseDto> policies = couponPolicyService.getAllCouponPolicies();
        model.addAttribute("policies", policies);
        return "admin/coupon-policy/coupon-policy-list";
    }

    /**
     * 쿠폰 정책 생성 폼 페이지
     *
     * @return 쿠폰 정책 생성 페이지
     */
    @GetMapping("/create")
    public String showCreateForm() {
        return "admin/coupon-policy/create-coupon-policy";
    }

    /**
     * 쿠폰 정책 생성
     *
     * @param requestDto 쿠폰 정책 생성 요청 DTO
     * @return 쿠폰 정책 목록 페이지로 리다이렉트
     */
    @PostMapping
    public String createCouponPolicy(@ModelAttribute @Valid CreateCouponPolicyRequest requestDto) {
        couponPolicyService.createCouponPolicy(requestDto);
        return "redirect:/admin/coupon/policies"; // 생성 후 목록 페이지로 리다이렉트
    }


    /**
     * 쿠폰 정책 수정 폼 페이지
     *
     * @param policyId 쿠폰 정책 ID
     * @param model 뷰로 데이터를 전달하기 위한 모델
     * @return 쿠폰 정책 수정 페이지
     */
    @GetMapping("/edit/{policy-id}")
    public String showEditForm(@PathVariable("policy-id") Long policyId, Model model) {
        CouponPolicyResponseDto policy = couponPolicyService.getCouponPolicyById(policyId);
        model.addAttribute("policy", policy);
        return "admin/coupon-policy/edit-coupon-policy";
    }

    /**
     * 쿠폰 정책 수정
     *
     * @param policyId 쿠폰 정책 ID
     * @param requestDto 쿠폰 정책 수정 요청 DTO
     * @return 쿠폰 정책 목록 페이지로 리다이렉트
     */
    @PutMapping("/edit/{policy-id}")
    public String updateCouponPolicy(
            @PathVariable("policy-id") Long policyId,
            @ModelAttribute @Valid UpdateCouponPolicyRequest requestDto) {
        couponPolicyService.updateCouponPolicy(policyId, requestDto);
        return "redirect:/admin/coupon/policies";
    }

}
