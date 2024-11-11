package com.nhnacademy.twojopingfront.admin.shipment.adaptor;

import com.nhnacademy.twojopingfront.admin.shipment.dto.request.CarrierRequestDto;
import com.nhnacademy.twojopingfront.admin.shipment.dto.response.CarrierResponseDto;
import com.nhnacademy.twojopingfront.common.gateway.GatewayClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Arrays;

/**
 * CarrierAdaptor 클래스는 배송업체 관련 요청을 외부 게이트웨이로 전달하는 역할을 합니다.
 * GatewayClient를 사용하여 배송업체 요청을 게이트웨이로 전송하고, 응답을 반환받습니다.
 */
@Component
@RequiredArgsConstructor
public class CarrierAdaptor {

    private final GatewayClient gatewayClient;

    private static final String BASE_ENDPOINT = "/v1/bookstore/carriers";

    /**
     * 모든 배송업체 정보를 조회합니다.
     *
     * @return 배송업체 리스트
     */
    public List<CarrierResponseDto> getAllCarriers() {
        ResponseEntity<CarrierResponseDto[]> response = gatewayClient.sendToGateway(
                HttpMethod.GET, BASE_ENDPOINT, null, CarrierResponseDto[].class
        );
        return Arrays.asList(response.getBody());
    }

    /**
     * 특정 ID의 배송업체 정보를 조회합니다.
     *
     * @param carrierId 조회할 배송업체의 ID
     * @return 배송업체 정보
     */
    public CarrierResponseDto getCarrier(Long carrierId) {
        String endpoint = BASE_ENDPOINT + "/" + carrierId;
        ResponseEntity<CarrierResponseDto> response = gatewayClient.sendToGateway(
                HttpMethod.GET, endpoint, null, CarrierResponseDto.class
        );
        return response.getBody();
    }

    /**
     * 새로운 배송업체를 생성합니다.
     *
     * @param carrierRequestDto 생성할 배송업체 정보
     */
    public void createCarrier(CarrierRequestDto carrierRequestDto) {
        gatewayClient.sendToGateway(HttpMethod.POST, BASE_ENDPOINT, carrierRequestDto, Void.class);
    }

    /**
     * 특정 ID의 배송업체 정보를 수정합니다.
     *
     * @param carrierId 수정할 배송업체의 ID
     * @param carrierRequestDto 수정할 배송업체 정보
     */
    public void updateCarrier(Long carrierId, CarrierRequestDto carrierRequestDto) {
        String endpoint = BASE_ENDPOINT + "/" + carrierId;
        gatewayClient.sendToGateway(HttpMethod.PUT, endpoint, carrierRequestDto, Void.class);
    }

    /**
     * 특정 ID의 배송업체를 삭제합니다.
     *
     * @param carrierId 삭제할 배송업체의 ID
     */
    public void deleteCarrier(Long carrierId) {
        String endpoint = BASE_ENDPOINT + "/" + carrierId;
        gatewayClient.sendToGateway(HttpMethod.DELETE, endpoint, null, Void.class);
    }
}
