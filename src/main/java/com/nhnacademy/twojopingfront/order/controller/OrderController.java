package com.nhnacademy.twojopingfront.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.twojopingfront.order.dto.request.PaymentRequest;
import com.nhnacademy.twojopingfront.order.dto.response.PaymentResponse;
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

@Controller
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final ObjectMapper objectMapper;

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

    @GetMapping("/form")
    public String form(Model model) {
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
