package com.nhnacademy.twojopingfront.admin.couponPolicy.service;

import com.nhnacademy.twojopingfront.admin.couponPolicy.client.CouponPolicyClient;
import com.nhnacademy.twojopingfront.admin.couponPolicy.dto.CouponPolicyResponseDto;
import com.nhnacademy.twojopingfront.admin.couponPolicy.dto.CreateCouponPolicyRequest;
import com.nhnacademy.twojopingfront.admin.couponPolicy.dto.UpdateCouponPolicyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Coupon Policy 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class CouponPolicyService {
    private final CouponPolicyClient couponPolicyClient;

    /**
     * 모든 쿠폰 정책 조회
     *
     * @return List<CouponPolicyResponseDto> 모든 쿠폰 정책 목록
     */
    public List<CouponPolicyResponseDto> getAllCouponPolicies() {
        return couponPolicyClient.getAllPolicies();
    }

    /**
     * 특정 쿠폰 정책 조회
     *
     * @param policyId 조회할 쿠폰 정책의 ID
     * @return CouponPolicyResponseDto 쿠폰 정책 정보
     */
    public CouponPolicyResponseDto getCouponPolicyById(Long policyId) {
        return couponPolicyClient.getCouponPolicy(policyId);
    }

    /**
     * 쿠폰 정책 생성
     *
     * @param requestDto 쿠폰 정책 생성 요청 DTO
     */
    public void createCouponPolicy(CreateCouponPolicyRequest requestDto) {
        couponPolicyClient.createCouponPolicy(requestDto);
    }

    /**
     * 쿠폰 정책 수정
     *
     * @param policyId 수정할 쿠폰 정책의 ID
     * @param requestDto 수정 내용이 포함된 요청 DTO
     */
    public void updateCouponPolicy(Long policyId, UpdateCouponPolicyRequest requestDto) {
        couponPolicyClient.updateCouponPolicy(policyId, requestDto);
    }


}
