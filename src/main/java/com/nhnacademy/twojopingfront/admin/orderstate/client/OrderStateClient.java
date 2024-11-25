//package com.nhnacademy.twojopingfront.admin.orderstate.client;
//
//import com.nhnacademy.twojopingfront.admin.orderstate.dto.request.CreateOrderStateRequest;
//import com.nhnacademy.twojopingfront.admin.orderstate.dto.request.UpdateOrderStateRequest;
//import com.nhnacademy.twojopingfront.admin.orderstate.dto.response.GetAllOrderStatesResponse;
//import com.nhnacademy.twojopingfront.admin.orderstate.dto.response.GetOrderStateResponse;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@FeignClient(name = "orderstate-service", url = "${gateway.base-url}")
//public interface OrderStateClient {
//
//    @PostMapping("/api/v1/orders/states")
//    ResponseEntity<Void> createOrderState(@RequestBody CreateOrderStateRequest request);
//
//    @GetMapping("/api/v1/orders/states")
//    ResponseEntity<GetAllOrderStatesResponse> getAllOrderStates();
//
//    @GetMapping("/api/v1/orders/states/{order-state-id}")
//    ResponseEntity<GetOrderStateResponse> getOrderState(@PathVariable("order-state-id") Long orderStateId);
//
//    @PutMapping("/api/v1/orders/states/{order-state-id}")
//    ResponseEntity<Void> updateOrderState(
//            @PathVariable("order-state-id") Long orderStateId,
//            @RequestBody UpdateOrderStateRequest request
//    );
//
//    @DeleteMapping("/api/v1/orders/states/{order-state-id}")
//    ResponseEntity<Void> deleteOrderState(@PathVariable("order-state-id") Long orderStateId);
//}