package com.nhnacademy.twojopingfront.admin.shipment.client;

import com.nhnacademy.twojopingfront.admin.shipment.dto.request.CarrierRequestDto;
import com.nhnacademy.twojopingfront.admin.shipment.dto.response.CarrierResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "CarrierClient", url = "${gateway.base-url}")
public interface CarrierClient {

    @GetMapping("/v1/bookstore/carriers")
    List<CarrierResponseDto> getAllCarriers();

    @GetMapping("/v1/bookstore/carriers/{carrierId}")
    CarrierResponseDto getCarrier(@PathVariable("carrierId") Long carrierId);

    @PostMapping("/v1/bookstore/carriers")
    void createCarrier(@RequestBody CarrierRequestDto carrierRequestDto);

    @PutMapping("/v1/bookstore/carriers/{carrierId}")
    void updateCarrier(@PathVariable("carrierId") Long carrierId, @RequestBody CarrierRequestDto carrierRequestDto);

    @DeleteMapping("/v1/bookstore/carriers/{carrierId}")
    void deleteCarrier(@PathVariable("carrierId") Long carrierId);
}
