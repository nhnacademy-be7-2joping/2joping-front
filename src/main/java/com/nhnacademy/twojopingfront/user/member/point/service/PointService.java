package com.nhnacademy.twojopingfront.user.member.point.service;

import com.nhnacademy.twojopingfront.user.member.point.client.MemberPointSimpleHistoryClient;
import com.nhnacademy.twojopingfront.user.member.point.dto.GetMyPageSimplePointHistoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final MemberPointSimpleHistoryClient memberPointSimpleHistoryClient;

    /**
     * 회원의 포인트 간략 정보를 가져옵니다.
     *
     * @param customerId 고객 ID
     * @return 포인트 간략 정보 DTO
     */
    public GetMyPageSimplePointHistoriesResponse getMyPageSimplePointHistories(Long customerId) {
        ResponseEntity<GetMyPageSimplePointHistoriesResponse> responseEntity =
                memberPointSimpleHistoryClient.getSimplePointHistories(String.valueOf(customerId));

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("포인트 정보를 가져오는 데 실패했습니다.");
        }
    }

}
