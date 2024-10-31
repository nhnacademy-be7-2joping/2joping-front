package com.nhnacademy.twojopingfront.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * RedirectOnError는 특정 메서드가 실행되는 중 오류가 발생했을 때 사용자가 설정한 URL로 리다이렉트를 수행하기 위해 사용됩니다.
 *
 * 이 어노테이션은 주로 회원 가입과 같은 입력 폼에서 유효성 검사 오류 발생 시, 동일 페이지로 리다이렉트하면서
 * 기존 입력 값을 유지하고 오류 메시지를 함께 제공하는 용도로 사용됩니다.
 * @author Luha
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedirectOnError {
    String url();
}