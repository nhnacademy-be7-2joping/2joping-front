package com.nhnacademy.twojopingfront.common.error.handler;

import com.nhnacademy.twojopingfront.common.annotation.RedirectOnError;
import com.nhnacademy.twojopingfront.common.error.dto.ClientErrorMessage;
import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomApiException;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomFeignException;
import com.nhnacademy.twojopingfront.common.error.exception.jwt.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GlobalExceptionHandler는 유효성 검사 실패나 API 오류와 같은 전역 예외를 처리하기 위한 핸들러 클래스입니다.
 * 각 예외 유형에 맞는 적절한 응답과 리다이렉트를 설정하여 사용자에게 친화적인 에러 처리를 제공합니다.
 *
 * @author Luha
 * @since 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    /**
     * 유효성 검사 실패 시 호출되는 메서드로, 검증 실패한 필드와 메시지를 수집하여 리다이렉트 페이지로 전달합니다.
     *
     * @param ex                 발생한 유효성 검사 예외 (MethodArgumentNotValidException)
     * @param redirectAttributes 리다이렉트 시 전달할 데이터를 저장하는 객체
     *                           RedirectOnError 어노테이션의 URL로 리다이렉트
     * @return 리다이렉트 URL
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(MethodArgumentNotValidException ex, RedirectAttributes redirectAttributes
            , Model model) {
        BindingResult result = ex.getBindingResult();

        Map<String, String> errors = result.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing // 기존 값 유지
                ));

        Object target = result.getTarget();
        if (target != null) {
            for (Field field : target.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    redirectAttributes.addFlashAttribute(field.getName(), field.get(target));
                } catch (IllegalAccessException ignored) {
                }
            }
        }

        redirectAttributes.addFlashAttribute("errors", errors);

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HandlerMethod handlerMethod =
                (HandlerMethod) request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);

        RedirectOnError redirectOnError = handlerMethod.getMethodAnnotation(RedirectOnError.class);
        String redirectUrl = (redirectOnError != null) ? redirectOnError.url() : "/";

        return "redirect:" + redirectUrl;
    }

    /**
     * 백엔드 서버로부터 온 errorResponse를 CustomResponseErrorHandler에서 잡아서 다시 CustomApiException로 던진다.
     * CustomApiException을 처리하는 메서드로, 클라이언트에게 전달할 에러 메시지를 설정하고 리다이렉트 또는 포워드를 결정합니다.
     *
     * @param ex 발생한 API 예외
     * @return 리다이렉트 또는 포워드 경로
     */
    @ExceptionHandler(CustomApiException.class)
    public String handleCustomApiException(CustomApiException ex, RedirectAttributes redirectAttributes, Model model) {

        redirectAttributes.addFlashAttribute("errorCode", ex.getErrorResponse().errorCode());
        redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorResponse().errorMessage());

        ClientErrorMessage clientError = new ClientErrorMessage(
                ex.getErrorResponse().errorCode(),
                ex.getErrorResponse().errorMessage()
        );
        Map<String, Object> inputData = (Map<String, Object>) ex.getErrorResponse().data();
        if (inputData != null) {
            inputData.forEach(redirectAttributes::addFlashAttribute);
        }
        model.addAttribute("errorResponse", clientError);

        if ("REDIRECT".equals(ex.getErrorResponse().redirectType().toString())) {


            return "redirect:" + ex.getErrorResponse().url();

        } else if ("FORWARD".equals(ex.getErrorResponse().redirectType().toString())) {

            return "forward:" + ex.getErrorResponse().url();

        } else {
            return "common/error";
        }
    }

    @ExceptionHandler(CustomFeignException.class)
    @ResponseBody
    public ErrorResponseDto<Void> getErrorResponse(CustomFeignException e, HttpServletResponse response) {
        response.setStatus(e.getErrorResponse().status());
        return e.getErrorResponse();
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, RedirectAttributes redirectAttributes, Model model) {
        ClientErrorMessage clientError = new ClientErrorMessage(
                "400", "알 수 없는 에러가 발생했습니다."
        );
        model.addAttribute("errorResponse", clientError);
        return "common/error";
    }
}
