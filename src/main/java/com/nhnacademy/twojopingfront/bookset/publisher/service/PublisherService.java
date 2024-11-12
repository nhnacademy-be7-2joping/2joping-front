package com.nhnacademy.twojopingfront.bookset.publisher.service;


import com.nhnacademy.twojopingfront.bookset.publisher.client.PublisherClient;
import com.nhnacademy.twojopingfront.bookset.publisher.dto.request.PublisherRequestDto;
import com.nhnacademy.twojopingfront.bookset.publisher.dto.response.PublisherCreateResponseDto;
import com.nhnacademy.twojopingfront.bookset.publisher.dto.response.PublisherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherClient publisherClient;

    /**
     * 새로운 출판사를 등록하는 메서드
     * @param publisherRequestDto 등록할 출판사의 정보
     * @return 등록된 출판사 정보
     */
    public PublisherCreateResponseDto registerPublisher(PublisherRequestDto publisherRequestDto) {
        return publisherClient.registerPublisher(publisherRequestDto);
    }

    /**
     * 전체 출판사 목록을 조회하는 메서드
     * @return 출판사 목록
     */
    public Page<PublisherResponseDto> getAllPublishers(int page, int size) {
        return publisherClient.getAllPublishers(page,size);
    }

    /**
     * 특정 출판사를 조회하는 메서드
     * @param id 조회할 출판사의 ID
     * @return 조회된 출판사 정보
     */
    public PublisherResponseDto getPublisher(Long id) {
        return publisherClient.getPublisher(id);
    }

    /**
     * 특정 출판사를 삭제하는 메서드
     * @param id 삭제할 출판사의 ID
     */
    public void deletePublisher(Long id) {
        publisherClient.deletePublisher(id);
    }

    /**
     * 특정 출판사를 수정하는 메서드
     * @param id 수정할 출판사의 ID
     * @param publisherRequestDto 수정할 출판사 정보
     * @return 수정된 출판사 정보
     */
    public PublisherResponseDto updatePublisher(Long id, PublisherRequestDto publisherRequestDto) {
        return publisherClient.updatePublisher(id, publisherRequestDto);
    }
}
