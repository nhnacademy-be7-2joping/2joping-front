package com.nhnacademy.twojopingfront.admin.pointType.service;


import com.nhnacademy.twojopingfront.admin.pointType.client.PointTypeClient;
import com.nhnacademy.twojopingfront.admin.pointType.dto.PointTypeRequestDto;
import com.nhnacademy.twojopingfront.admin.pointType.dto.PointTypeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Point Type 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class PointTypeService {
    private final PointTypeClient pointTypeClient;

    public List<PointTypeResponseDto> getAllActivePointTypes() {
        return pointTypeClient.getAllActivePointType();
    }

    public PointTypeResponseDto getPointTypeById(Long typeId) {
        return pointTypeClient.getPointById(typeId);
    }

    public void createPointType(PointTypeRequestDto requestDto) {
        pointTypeClient.createPointType(requestDto);
    }

    public void updatePointType(Long typeId, PointTypeRequestDto requestDto) {
        pointTypeClient.updatePointType(typeId, requestDto);
    }

}
