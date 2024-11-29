package com.nhnacademy.twojopingfront.point.service;


import com.nhnacademy.twojopingfront.point.client.PointDetailClient;
import com.nhnacademy.twojopingfront.point.dto.response.GetMyPageDetailPointHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointDetailService {
    private final PointDetailClient pointDetailClient;

    /**
     * 포인트 상세 내역 가져오기
     *
     * @param customerId 고객 ID
     * @return 포인트 상세 내역 데이터
     */
    public GetMyPageDetailPointHistory getMyPageDetailPointHistory(Long customerId) {
        ResponseEntity<GetMyPageDetailPointHistory> responseEntity =
                pointDetailClient.getDetailPointHistories(String.valueOf(customerId));
        return responseEntity.getBody();
    }
}
