package com.nhnacademy.twojopingfront.common.config.thymeleaf;

import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * ThymeleafCustomFormatter
 *
 * Thymeleaf 템플릿에서 사용할 사용자 정의 포매터 클래스.
 * 숫자를 원하는 형식(예: 천 단위 구분 포함)으로 포맷팅하는 메서드를 제공합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Component
public class ThymeleafCustomFormatter {

    public String formatCurrency(int value) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
        return numberFormat.format(value);
    }
}