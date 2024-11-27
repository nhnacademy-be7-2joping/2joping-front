package com.nhnacademy.twojopingfront.bookset.contributor.service;

import com.nhnacademy.twojopingfront.bookset.contributor.client.ContributorRoleClient;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.request.ContributorRoleRequestDto;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.response.ContributorRoleResponseDto;
import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import com.nhnacademy.twojopingfront.common.error.enums.RedirectType;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContributorRoleService {
    private final ContributorRoleClient contributorRoleClient;

    public void createContributorRole(ContributorRoleRequestDto requestDto) {
        try {
            contributorRoleClient.createContributorRole(requestDto);
        } catch (Exception e) {
            throw contributorRoleCustomApiException("ROLE_CREATE_ERROR", "중복된 도서 기여자 역할 이름으로 생성할 수 없습니다.");
        }
    }

    public ContributorRoleResponseDto getContributorRole(Long contributorRoleId) {
        try {
            return contributorRoleClient.getContributorRole(contributorRoleId).getBody();
        } catch (Exception e) {
            throw contributorRoleCustomApiException("ROLE_FETCH_ERROR", "기여자 역할 조회 중 오류가 발생했습니다.");
        }
    }

    public void updateContributorRole(Long contributorRoleId, ContributorRoleRequestDto requestDto) {
        try {
            contributorRoleClient.updateContributorRole(contributorRoleId, requestDto);
        } catch (Exception e) {
            throw contributorRoleCustomApiException("ROLE_UPDATE_ERROR", "중복된 도서 기여자 역할 이름으로 수정할 수 없습니다.");
        }
    }

    public void deleteContributorRole(Long contributorRoleId) {
        try {
            contributorRoleClient.deleteContributorRole(contributorRoleId);
        } catch (Exception e) {
            throw contributorRoleCustomApiException("ROLE_DELETE_ERROR", "기여자 역할 삭제 중 오류가 발생했습니다.");
        }
    }

    public List<ContributorRoleResponseDto> getAllContributorRoles() {
        try {
            return contributorRoleClient.getAllContributorRoles().getBody();
        } catch (Exception e) {
            throw contributorRoleCustomApiException("ROLE_FETCH_ALL_ERROR", "기여자 역할 목록 조회 중 오류가 발생했습니다.");
        }
    }

    /**
     * CustomApiException 생성 메서드
     *
     * @param errorCode    사용자 정의 오류 코드
     * @param errorMessage 오류 메시지
     * @return 생성된 CustomApiException
     */
    private CustomApiException contributorRoleCustomApiException(String errorCode, String errorMessage) {
        ErrorResponseDto<?> errorResponse = new ErrorResponseDto<>(
                500,
                errorCode,
                errorMessage,
                RedirectType.REDIRECT,
                "/admin/contributors/role",
                null
        );
        return new CustomApiException(errorResponse);
    }
}
