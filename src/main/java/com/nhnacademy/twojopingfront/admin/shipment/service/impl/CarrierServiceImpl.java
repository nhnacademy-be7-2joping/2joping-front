package com.nhnacademy.twojopingfront.admin.shipment.service.impl;

import com.nhnacademy.twojopingfront.admin.shipment.adaptor.CarrierAdaptor;
import com.nhnacademy.twojopingfront.admin.shipment.dto.request.CarrierRequestDto;
import com.nhnacademy.twojopingfront.admin.shipment.dto.response.CarrierResponseDto;
import com.nhnacademy.twojopingfront.admin.shipment.service.CarrierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarrierServiceImpl implements CarrierService {

    private final CarrierAdaptor carrierAdaptor;

    @Override
    public List<CarrierResponseDto> getAllCarriers() {
        return carrierAdaptor.getAllCarriers();
    }

    @Override
    public CarrierResponseDto getCarrier(Long id) {
        return carrierAdaptor.getCarrier(id);
    }

    @Override
    public void createCarrier(CarrierRequestDto carrierRequestDto) {
        carrierAdaptor.createCarrier(carrierRequestDto);
    }

    @Override
    public void updateCarrier(Long id, CarrierRequestDto carrierRequestDto) {
        carrierAdaptor.updateCarrier(id, carrierRequestDto);
    }

    @Override
    public void deleteCarrier(Long id) {
        carrierAdaptor.deleteCarrier(id);
    }
}
