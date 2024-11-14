package com.nhnacademy.twojopingfront.admin.wrap.client;

import com.nhnacademy.twojopingfront.admin.wrap.dto.WrapRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.WrapResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "WrapClient", url = "${gateway-url}")
public interface WrapClient {
    @PostMapping("/api/v1/wraps")
    ResponseEntity<WrapResponseDto> createWrap(@Valid @RequestBody WrapRequestDto requestDto);

    @GetMapping("/api/v1/wraps/{wrap-id}")
    ResponseEntity<WrapResponseDto> getWrap(@PathVariable("wrap-id") Long wrapId);

    @GetMapping("/api/v1/wraps")
    ResponseEntity<List<WrapResponseDto>> findAllByIsActiveTrue();

    @PutMapping("/api/v1/wraps/{wrap-id}")
        //수정
    ResponseEntity<WrapResponseDto> updateWrap(@PathVariable("wrap-id") Long wrapId, @RequestBody WrapRequestDto requestDto);

}