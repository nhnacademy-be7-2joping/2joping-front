package com.nhnacademy.twojopingfront.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.twojopingfront.cart.entity.Book;
import com.nhnacademy.twojopingfront.cart.entity.Cart;
import com.nhnacademy.twojopingfront.cart.service.CartService;
import com.nhnacademy.twojopingfront.order.client.ShipmentPolicyRequestClient;
import com.nhnacademy.twojopingfront.order.client.WrapClient;
import com.nhnacademy.twojopingfront.order.dto.request.PaymentRequest;
import com.nhnacademy.twojopingfront.order.dto.response.PaymentResponse;
import com.nhnacademy.twojopingfront.order.dto.response.ShipmentPolicyResponseDto;
import com.nhnacademy.twojopingfront.order.dto.response.WrapResponseDto;
import com.nhnacademy.twojopingfront.user.dto.request.LoginNonMemberRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final ObjectMapper objectMapper;
    private final CartService cartService;
    private final ShipmentPolicyRequestClient shipmentPolicyRequestClient;
    private final WrapClient wrapClient;

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

        return "customer-orders";
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
    public String form(Model model) {
        List<Cart> cartItems = cartService.getCartByCustomerId(1);
        int bookCost = cartItems.stream().map(i -> i.getBook().getSellingPrice() * i.getQuantity()).reduce(
                0,
                Integer::sum
        ); // 주문한 상품들의 총 가격
        List<WrapResponseDto> wrapResponseDtos = wrapClient.getAllWraps().getBody();
        List<Book> wrappableBooks = cartItems.stream().map(Cart::getBook).filter(Book::isGiftWrappable).toList();
        List<ShipmentPolicyResponseDto> shipmentPolicyResponseDtos =
                shipmentPolicyRequestClient.getAllShipmentPolicies(true).getBody();
        int appliedDeliveryCost = 0;
        // 배송 정책 최소 적용 가격 기준으로 정렬
        Objects.requireNonNull(shipmentPolicyResponseDtos).sort((p1, p2) -> p1.minOrderAmount() - p2.minOrderAmount());
        for (ShipmentPolicyResponseDto dto : shipmentPolicyResponseDtos) {
            if (bookCost >= dto.minOrderAmount()) {
                appliedDeliveryCost = dto.shippingFee();
            }
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("bookCost", bookCost);
        model.addAttribute("deliveryCost", appliedDeliveryCost);
        model.addAttribute("wraps", wrapResponseDtos);
        model.addAttribute("wrappableBooks", wrappableBooks);
        model.addAttribute("shipmentPolicies", shipmentPolicyResponseDtos);
        // 회원이 가진 쿠폰 정보 모델에 적용 필요

        return "order/order-form";
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
