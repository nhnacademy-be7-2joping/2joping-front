package com.nhnacademy.twojopingfront.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {
    @GetMapping("/success")
    public String success(@RequestParam("paymentType") String paymentType,
                          @RequestParam("orderId") String orderId, @RequestParam("paymentKey") String paymentKey,
                          @RequestParam("amount") int amount, Model model) {
        model.addAttribute("paymentType", paymentType);
        model.addAttribute("orderId", orderId);
        model.addAttribute("paymentKey", paymentKey);
        model.addAttribute("amount", amount);

        return "order/payment-success";
    }
}
