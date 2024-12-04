package com.nhnacademy.twojopingfront.admin.wrap.client;

import com.nhnacademy.twojopingfront.admin.wrap.dto.request.WrapModifyRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.request.WrapUpdateRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.response.WrapCreateResponseDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.request.WrapDetailRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.request.WrapRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.response.WrapResponseDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.response.WrapUpdateResponseDto;
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
    WrapCreateResponseDto createWrap(@Valid @RequestBody WrapRequestDto requestDto);

    @GetMapping("/v1/wraps/{wrap-id}")
    WrapUpdateResponseDto getWrap(@PathVariable("wrap-id") Long wrapId);

    @PutMapping("/v1/wraps/{wrap-id}")
    WrapUpdateResponseDto updateWrap(@PathVariable("wrap-id") Long wrapId,
                                     @Valid @RequestBody WrapUpdateRequestDto wrapUpdateRequestDto);

    @GetMapping("/v1/wraps")
    List<WrapUpdateResponseDto> findAllByIsActiveTrue();

    @PutMapping("/v1/wraps/{wrap-id}")
    ResponseEntity<WrapResponseDto> updateWrap(@PathVariable("wrap-id") Long wrapId, @RequestBody WrapModifyRequestDto wrapModifyRequestDto);

}