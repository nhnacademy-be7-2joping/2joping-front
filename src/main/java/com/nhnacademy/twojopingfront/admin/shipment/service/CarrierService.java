package com.nhnacademy.twojopingfront.admin.shipment.service;

import com.nhnacademy.twojopingfront.admin.shipment.dto.request.CarrierRequestDto;
import com.nhnacademy.twojopingfront.admin.shipment.dto.response.CarrierResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarrierService {

    List<CarrierResponseDto> getAllCarriers();

    CarrierResponseDto getCarrier(Long id);

    void createCarrier(CarrierRequestDto carrierRequestDto);

    void updateCarrier(Long id, CarrierRequestDto carrierRequestDto);

    void deleteCarrier(Long id);
}
