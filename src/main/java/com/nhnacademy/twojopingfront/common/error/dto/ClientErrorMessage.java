package com.nhnacademy.twojopingfront.common.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClientErrorMessage는 클라이언트에게 전달할 에러 정보를 담기 위한 DTO 클래스입니다.
 * 불필요한 서버 내부 정보는 제외하고, 클라이언트가 이해할 수 있는 에러 코드와 메시지만을 제공하여 보안성을 강화합니다.
 *
 *  errorCode    클라이언트에게 전달할 에러 코드 (예: "BAD_REQUEST")
 *  errorMessage 클라이언트에게 보여줄 에러 메시지 (예: "입력 값이 잘못되었습니다.")
 * @author Luha
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ClientErrorMessage {
    private final String errorCode;
    private final String errorMessage;
}