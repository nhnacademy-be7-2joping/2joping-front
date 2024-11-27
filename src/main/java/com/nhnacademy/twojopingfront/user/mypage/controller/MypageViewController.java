package com.nhnacademy.twojopingfront.user.mypage.controller;

import com.nhnacademy.twojopingfront.common.util.MemberUtils;
import com.nhnacademy.twojopingfront.order_detail.dto.response.OrderDetailResponseDto;
import com.nhnacademy.twojopingfront.order_detail.service.OrderDetailService;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewResponseDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewTotalResponseDto;
import com.nhnacademy.twojopingfront.review.service.ReviewService;
import com.nhnacademy.twojopingfront.tier.adaptor.TierAdaptor;
import com.nhnacademy.twojopingfront.tier.dto.response.MemberTierResponse;
import com.nhnacademy.twojopingfront.user.member.adaptor.MemberAdaptor;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberAddressResponseDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberCouponResponseDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberUpdateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


/**
 * 마이페이지 관련 뷰를 처리하는 컨트롤러입니다.
 * 사용자의 마이페이지에 대한 다양한 기능(회원 정보, 주문 목록, 주소 목록 등)을 처리하며,
 * 각 기능에 대한 View를 반환합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageViewController {

    private final MemberAdaptor memberAdaptor;
    private final TierAdaptor tierAdaptor;
    private final ReviewService reviewService;
    private final OrderDetailService orderDetailService;
    /**
     * 마이페이지 메인 화면으로 이동합니다.
     *
     * @param model 뷰에 데이터를 전달하는 모델 객체
     * @return 마이페이지 메인 화면 경로
     */
    @Operation(summary = "마이페이지 메인 화면", description = "사용자의 마이페이지 메인 화면으로 이동합니다.")
    @GetMapping
    public String mypageView(Model model) {
        MemberTierResponse tierResponse = tierAdaptor.getMemberTier();
        model.addAttribute("tier", tierResponse);

        Long customerId = MemberUtils.getCustomerId();
        List<OrderDetailResponseDto> orderDetails = orderDetailService.getOrderDetailsByCustomerId(customerId.toString());
        model.addAttribute("orderDetails", orderDetails);
        return "user/mypage/mypage";
    }

    /**
     * 회원 정보 수정 페이지로 이동합니다.
     *
     * @param model 뷰에 데이터를 전달하는 모델 객체
     * @return 회원 정보 수정 화면 경로
     */
    @Operation(summary = "회원 정보 수정 페이지", description = "사용자가 자신의 정보를 수정할 수 있는 페이지로 이동합니다.")
    @GetMapping("/edit-profile")
    public String editProfileView(Model model) {
        MemberUpdateResponseDto memberInfo = memberAdaptor.getMember();
        model.addAttribute("memberInfo", memberInfo);

        return "user/mypage/edit-profile";
    }

    /**
     * 주문 목록 페이지로 이동합니다.
     *
     * @param model 뷰에 데이터를 전달하는 모델 객체
     * @return 주문 목록 화면 경로
     */
    @Operation(summary = "주문 목록 페이지", description = "사용자가 자신의 주문 내역을 조회할 수 있는 페이지로 이동합니다.")
    @GetMapping("/order-list")
    public String orderListView(Model model) {

        return "user/mypage/order-list";
    }

    /**
     * 반품 및 교환 내역 페이지로 이동합니다.
     *
     * @param model 뷰에 데이터를 전달하는 모델 객체
     * @return 반품 및 교환 내역 화면 경로
     */
    @Operation(summary = "반품 및 교환 내역 페이지", description = "사용자가 반품 및 교환 내역을 조회할 수 있는 페이지로 이동합니다.")
    @GetMapping("/return-exchange-history")
    public String returnExchangeHistoryView(Model model) {

        return "user/mypage/return-exchange-history";
    }


    /**
     * 주소 목록 페이지로 이동합니다.
     * 사용자의 등록된 주소 데이터를 가져와 뷰에 전달합니다.
     *
     * @param model 뷰에 데이터를 전달하는 모델 객체
     * @return 주소 목록 화면 경로
     */
    @Operation(summary = "주소 목록 페이지", description = "사용자가 자신의 주소 목록을 조회하고 관리할 수 있는 페이지로 이동합니다.")
    @GetMapping("/address-list")
    public String addressListView(Model model) {

        List<MemberAddressResponseDto> addresses = memberAdaptor.getMemberAddresses();
        model.addAttribute("addresses", addresses);
        return "user/mypage/address-list";
    }

    /**
     * 리뷰 내역 페이지로 이동합니다.
     *
     * @param model 뷰에 데이터를 전달하는 모델 객체
     * @return 리뷰 내역 화면 경로
     */
    @Operation(summary = "리뷰 내역 페이지", description = "사용자가 작성한 리뷰 내역을 조회할 수 있는 페이지로 이동합니다.")
    @GetMapping("/review-history")
    public String reviewHistoryView(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    Model model) {
        Long customerId = MemberUtils.getCustomerId();

        Page<ReviewTotalResponseDto> reviews = reviewService.getReviewsByCustomerId(page, size, customerId.toString());
        model.addAttribute("reviews", reviews);
        return "user/mypage/review-history";
    }
    @GetMapping("/withdraw")
    public String withdrawView(Model model) {

        return "user/mypage/withdraw";
    }
    @GetMapping("/my-coupon")
    public String couponListView(Model model) {
        List<MemberCouponResponseDto> coupons = memberAdaptor.getMemberCoupon();
        List<MemberCouponResponseDto> usedCoupons = memberAdaptor.getMemberUsedCoupon();

        model.addAttribute("coupons", coupons);
        model.addAttribute("usedCoupons", usedCoupons);

        return "user/mypage/my-coupon";
    }
}
