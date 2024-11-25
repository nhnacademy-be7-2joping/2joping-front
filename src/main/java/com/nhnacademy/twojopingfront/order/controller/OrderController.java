package com.nhnacademy.twojopingfront.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.service.BookService;
import com.nhnacademy.twojopingfront.cart.client.CartClient;
import com.nhnacademy.twojopingfront.cart.dto.CartResponseDto;
import com.nhnacademy.twojopingfront.common.util.MemberUtils;
import com.nhnacademy.twojopingfront.order.client.MemberClient;
import com.nhnacademy.twojopingfront.order.client.MemberCouponClient;
import com.nhnacademy.twojopingfront.order.client.ShipmentPolicyRequestClient;
import com.nhnacademy.twojopingfront.order.client.WrapClient;
import com.nhnacademy.twojopingfront.order.dto.request.PaymentRequest;
import com.nhnacademy.twojopingfront.order.dto.response.*;
import com.nhnacademy.twojopingfront.user.login.dto.request.LoginNonMemberRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * 주문과 관련된 컨트롤러로, 주문 시 보여줄 view와 관련된 페이지
 * 주문에 대한 조회, 등록, 결제 승인 등의 처리를 맡는다.
 *
 * @author Sauter001
 * @since 1.0
 */
@Controller
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final ObjectMapper objectMapper;
    private final BookService bookService;
    private final ShipmentPolicyRequestClient shipmentPolicyRequestClient;
    private final WrapClient wrapClient;
    private final MemberCouponClient memberCouponClient;
    private final MemberClient memberClient;
    private final CartClient cartClient;

    @Value("${toss.widget-secret-key}")
    private String widgetSecretKey;

    /**
     * 비회원 주문 조회를 위한 API
     *
     * @param nonMemberRequestDto 비회원 주문조회 페이지에서 받은 정보
     * @param model
     * @return 비회원 주문 정보 페이지
     */
    @GetMapping
    public String orders(@RequestBody LoginNonMemberRequestDto nonMemberRequestDto, Model model) {
        // order 조회 API 필요

        return "nonmember-orders";
    }

    /**
     * 주문 작성 폼
     *
     * @param model 주문 시 필요한 정보를 가져올 모델이다. 장바구니에 담은 책, 총 가격, 배송비, 포장 종류,
     *              포장 가능한 도서, 배송 정책 정보 필요
     * @return 주문 페이지 뷰
     * @author 이승준
     */
    @GetMapping("/form")
    public String form(@RequestParam(value = "bookId", required = false) Long bookId,
                       @RequestParam(value = "quantity", required = false, defaultValue = "1") Integer quantity,
                       @CookieValue(value = "cartSession", required = false) String cartSession,
                       Model model) {
        List<CartResponseDto> cartItems = getCartItems(bookId, cartSession, quantity);
        List<BookResponseDto> wrappableBooks =
                cartItems.stream().map(CartResponseDto::bookId)
                        .map(bookService::getBookById).filter(BookResponseDto::giftWrappable).toList();
        List<WrapResponseDto> wrapResponseDtos = wrapClient.getAllWraps().getBody();
        List<ShipmentPolicyResponseDto> shipmentPolicyResponseDtos =
                shipmentPolicyRequestClient.getAllShipmentPolicies(!MemberUtils.isAnonymous()).getBody();
        List<OrderCouponResponse> coupons = getMemberCoupons();
        MemberPointResponse pointResponse = MemberUtils.isAnonymous() ? new MemberPointResponse(-1) :
                memberClient.getPoints().getBody();

        int appliedDeliveryCost = 0;
        long appliedDeliveryPolicyId = 0;

        int bookCost = cartItems.stream().map(i -> i.sellingPrice() * i.quantity()).reduce(
                0,
                Integer::sum
        ); // 주문한 상품들의 총 가격

        // 배송 정책 최소 적용 가격 기준으로 정렬 및 책정
        Objects.requireNonNull(shipmentPolicyResponseDtos).sort((p1, p2) -> p1.minOrderAmount() - p2.minOrderAmount());
        for (ShipmentPolicyResponseDto dto : shipmentPolicyResponseDtos) {
            if (bookCost >= dto.minOrderAmount()) {
                appliedDeliveryCost = dto.shippingFee();
                appliedDeliveryPolicyId = dto.shipmentPolicyId();
            }
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("bookCost", bookCost);
        model.addAttribute("deliveryCost", appliedDeliveryCost);
        model.addAttribute("deliveryPolicyId", appliedDeliveryPolicyId);
        model.addAttribute("wraps", wrapResponseDtos);
        model.addAttribute("wrappableBooks", wrappableBooks);
        model.addAttribute("memberCoupons", coupons);
        model.addAttribute("shipmentPolicies", shipmentPolicyResponseDtos);
        model.addAttribute("points", pointResponse);

        return "order/order-form";
    }

    private List<CartResponseDto> getCartItems(Long bookId, String cartSession, int quantity) {
        if (bookId == null) {
            return cartClient.listCarts(cartSession).getBody();
        } else {
            BookResponseDto bookResponseDto = bookService.getBookById(bookId);
            return List.of(new CartResponseDto(
                    bookId,
                    bookResponseDto.title(),
                    bookResponseDto.sellingPrice(),
                    quantity
            ));
        }
    }

    private List<OrderCouponResponse> getMemberCoupons() {
        return MemberUtils.getCustomerId() < 0 ? List.of() : memberCouponClient.getMemberCoupon().getBody();
    }

    /**
     * 결제 확인 시 toss와 연동하여 결제 승인 여부 결정
     *
     * @param paymentRequest 결제 정보가 담긴 dto
     * @return 결제 상태에 대한 응답 정보와 결제 key
     * @throws Exception
     */
    @PostMapping("/confirm")
    public ResponseEntity<PaymentResponse> orderConfirm(@RequestBody PaymentRequest paymentRequest) throws Exception {
        Base64.Encoder encoder = Base64.getEncoder();
        String authorizations =
                "Basic " + encoder.encodeToString((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));

        // 결제를 승인하면 결제수단에서 금액이 차감
        URL url = new URI("https://api.tosspayments.com/v1/payments/confirm").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // ObjectMapper를 사용하여 paymentRequest를 JSON 형식으로 변환
        OutputStream outputStream = connection.getOutputStream();
        objectMapper.writeValue(outputStream, paymentRequest);
        outputStream.flush();

        int code = connection.getResponseCode();
        boolean isSuccess = (code == 200);

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // ObjectMapper로 응답을 PaymentResponse로 변환
        PaymentResponse paymentResponse = objectMapper.readValue(responseStream, PaymentResponse.class);
        responseStream.close();

        return ResponseEntity.status(200).body(paymentResponse);
    }
}
