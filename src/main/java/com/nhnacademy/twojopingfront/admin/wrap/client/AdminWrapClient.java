package com.nhnacademy.twojopingfront.admin.wrap.client;

import com.nhnacademy.twojopingfront.admin.wrap.dto.WrapRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.WrapResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PropertySource("classpath:application.properties")

@FeignClient(name = "AdminWrapClient", url = "${gateway.base-url}")
public interface AdminWrapClient {
    @PostMapping("/v1/wraps")
    ResponseEntity<WrapResponseDto> createWrap(@Valid @RequestBody WrapRequestDto requestDto);

    @GetMapping("/v1/wraps/{wrap-id}")
    ResponseEntity<WrapResponseDto> getWrap(@PathVariable("wrap-id") Long wrapId);

    @GetMapping("/v1/wraps")
    ResponseEntity<List<WrapResponseDto>> findAllByIsActiveTrue();

    @PutMapping("/v1/wraps/{wrap-id}")
    ResponseEntity<WrapResponseDto> updateWrap(@PathVariable("wrap-id") Long wrapId, @RequestBody WrapRequestDto requestDto);

}