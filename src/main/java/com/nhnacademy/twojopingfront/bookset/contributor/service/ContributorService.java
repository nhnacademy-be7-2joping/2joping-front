package com.nhnacademy.twojopingfront.bookset.contributor.service;

import com.nhnacademy.twojopingfront.bookset.contributor.client.ContributorClient;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.request.ContributorRequestDto;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.response.ContributorResponseDto;
import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import com.nhnacademy.twojopingfront.common.error.enums.RedirectType;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContributorService {
    private final ContributorClient contributorClient;

    public ContributorResponseDto createContributor(ContributorRequestDto requestDto) {
        try {
            return contributorClient.createContributor(requestDto).getBody();
        } catch (Exception e) {
            throw contributorCustomApiException("CONTRIBUTOR_CREATE_ERROR", "도서 기여자 생성 중 오류가 발생했습니다.");
        }
    }

    public ContributorResponseDto getContributor(Long contributorId) {
        try {
            return contributorClient.getContributor(contributorId).getBody();
        } catch (Exception e) {
            throw contributorCustomApiException("CONTRIBUTOR_FETCH_ERROR", "도서 기여자 조회 중 오류가 발생했습니다.");
        }
    }

    public ContributorResponseDto updateContributor(Long contributorId, ContributorRequestDto requestDto) {
        try {
            return contributorClient.updateContributor(contributorId, requestDto).getBody();
        } catch (Exception e) {
            throw contributorCustomApiException("CONTRIBUTOR_UPDATE_ERROR", "도서 기여자 수정 중 오류가 발생했습니다.");
        }
    }

    public void deactivateContributor(Long contributorId) {
        try {
            contributorClient.deactivateContributor(contributorId);
        } catch (Exception e) {
            throw contributorCustomApiException("CONTRIBUTOR_DEACTIVATE_ERROR", "도서 기여자 비활성화 중 오류가 발생했습니다.");
        }
    }

    public void activateContributor(Long contributorId) {
        try {
            contributorClient.activateContributor(contributorId);
        } catch (Exception e) {
            throw contributorCustomApiException("CONTRIBUTOR_ACTIVATE_ERROR", "도서 기여자 활성화 중 오류가 발생했습니다.");
        }
    }

    public List<ContributorResponseDto> getActiveContributors() {
        try {
            return contributorClient.getActiveContributors().getBody();
        } catch (Exception e) {
            throw contributorCustomApiException("CONTRIBUTOR_ACTIVE_FETCH_ERROR", "활성화된 도서 기여자 조회 중 오류가 발생했습니다.");
        }
    }

    public Page<ContributorResponseDto> getAllContributors(int page, int size) {
        try {
            return contributorClient.getAllContributors(page, size).getBody();
        } catch (Exception e) {
            throw contributorCustomApiException("CONTRIBUTOR_FETCH_ALL_ERROR", "도서 기여자 목록 조회 중 오류가 발생했습니다.");
        }
    }

    private CustomApiException contributorCustomApiException(String errorCode, String errorMessage) {
        ErrorResponseDto<?> errorResponse = new ErrorResponseDto<>(
                500,
                errorCode,
                errorMessage,
                RedirectType.REDIRECT,
                "/admin/contributors",
                null
        );
        return new CustomApiException(errorResponse);
    }
}
