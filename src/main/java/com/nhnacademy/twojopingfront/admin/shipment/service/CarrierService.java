package com.nhnacademy.twojopingfront.admin.shipment.service;

import com.nhnacademy.twojopingfront.admin.shipment.client.CarrierClient;
import com.nhnacademy.twojopingfront.admin.shipment.dto.request.CarrierRequestDto;
import com.nhnacademy.twojopingfront.admin.shipment.dto.response.CarrierResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarrierService {

    private final CarrierClient carrierClient;

    public List<CarrierResponseDto> getAllCarriers() {
        return carrierClient.getAllCarriers();
    }

    public CarrierResponseDto getCarrier(Long id) {
        return carrierClient.getCarrier(id);
    }

    public void createCarrier(CarrierRequestDto carrierRequestDto) {
        carrierClient.createCarrier(carrierRequestDto);
    }

    public void updateCarrier(Long id, CarrierRequestDto carrierRequestDto) {
        carrierClient.updateCarrier(id, carrierRequestDto);
    }

    public void deleteCarrier(Long id) {
        carrierClient.deleteCarrier(id);
    }
}
