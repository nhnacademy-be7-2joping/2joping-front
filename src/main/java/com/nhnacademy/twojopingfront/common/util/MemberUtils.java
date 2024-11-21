package com.nhnacademy.twojopingfront.common.util;

import com.nhnacademy.twojopingfront.common.error.exception.user.UnauthorizedException;
import com.nhnacademy.twojopingfront.common.security.MemberUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 현재 로그인한 사용자의 정보에 접근하기 위한 util class
 * <p>
 * 이 클래스는 인증된 회원의 nickname, customer id(long) 등의 세부 정보를 가져오기 위해 사용한다.
 * 인증되지 않은 사용자(i.e. 익명 사용자)에 대해 이 유틸에 접근하는 경우 예외 발생함
 * </p>
 * <p>
 * ※ 이 클래스는 유틸 클래스로 인스턴스 호출해선 안 됨.
 * private이므로 직접적으로 생성자 호출할 수는 없지만, 인스턴스화하면 {@code IllegalStateException} 발생함
 * </p>
 *
 * @author 이승준
 * @version 1.0
 * @since 2024-11-19
 */
public class MemberUtils {
    private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * Utility class 생성 방지를 위한 private 생성자
     *
     * @throws IllegalStateException 생성자 호출 시 발생
     */
    private MemberUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 접속 중인 회원의 사용자 정보 가져옴
     * 사용자가 익명인 경우 {@link UnauthorizedException} 발생
     *
     * @return 회원의 {@link MemberUserDetails}
     * @throws UnauthorizedException 비회원 상태인 경우
     */
    public static MemberUserDetails getCurrentUser() {
        if (isAnonymous()) {
            throw new UnauthorizedException();
        }

        return (MemberUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 현재 접속한 사용자가 익명 사용자(ROLE_ANONYMOUS)인지 확인.
     *
     * @return 익명 사용자일 경우 {@code true}, 그렇지 않을 경우 {@code false}
     */
    private static boolean isAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(ROLE_ANONYMOUS));
    }

    /**
     * 접속 중인 회원의 사용자 ID 가져옴
     *
     * @return 회원 id. 인증되지 않은 경우 {@code -1}
     */
    public static Long getCustomerId() {
        try {
            return getCurrentUser().getId();
        } catch (UnauthorizedException e) {
            return -1L;
        }
    }

    /**
     * 접속 중인 회원의 닉네임을 가져옴
     *
     * @return 회원의 nickname, 인증되지 않은 경우 빈 문자열({@code ""})
     */
    public static String getNickname() {
        try {
            return getCurrentUser().getUsername();
        } catch (UnauthorizedException e) {
            return "";
        }
    }

    public static boolean isAdmin() {
        try {
            return getCurrentUser().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } catch (UnauthorizedException e) {
            return false;
        }
    }
}
