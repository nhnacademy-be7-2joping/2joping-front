package com.nhnacademy.twojopingfront.admin.pointType.client;


import com.nhnacademy.twojopingfront.admin.pointType.dto.PointTypeRequestDto;
import com.nhnacademy.twojopingfront.admin.pointType.dto.PointTypeResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "AdminPointTypeClient", url = "${gateway.base-url}" )
public interface PointTypeClient {
    @PostMapping("/v1/admin/pointtypes")
    ResponseEntity<PointTypeResponseDto> createPointType(@Valid @RequestBody PointTypeRequestDto requestDto);

    @PutMapping("/v1/admin/pointtypes/{type-id}")
    PointTypeResponseDto updatePointType(@PathVariable("type-id") Long typeId,
                                         @Valid @RequestBody PointTypeRequestDto requestDto);

    @GetMapping("/v1/admin/pointtypes")
    List<PointTypeResponseDto> getAllActivePointType();

    @GetMapping("/v1/admin/pointtypes/{type-id}")
    PointTypeResponseDto getPointById(@PathVariable("type-id") Long typeId);
}

