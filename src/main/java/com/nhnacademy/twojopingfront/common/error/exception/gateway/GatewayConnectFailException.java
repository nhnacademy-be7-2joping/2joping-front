package com.nhnacademy.twojopingfront.common.error.exception.gateway;

/**
 * GatewayConnectFailException은 게이트웨이와의 통신이 실패할 때 발생하는 예외입니다.
 * 외부 게이트웨이 서버에 요청을 전송하는 중 오류가 발생하면 이 예외가 던져집니다.
 * @author Luha
 * @since 1.0
 */
public class GatewayConnectFailException extends RuntimeException{

    /**
     * 예외 메시지를 받아 RuntimeException의 생성자로 전달합니다.
     *
     * @param message 예외 발생 시 출력할 상세 메시지
     */
    public GatewayConnectFailException(String message) {
        super(message);
    }
}
