package com.nhnacademy.twojopingfront.common.error.exception.image;

/**
 * ImageUploadException는 이미지 업로드가 실패할 때 발생하는 예외입니다.
 * @author 이초은
 */
public class ImageUploadException extends RuntimeException {
    public static final String MESSAGE = "이미지 업로드 중 오류가 발생했습니다.";

    public ImageUploadException() {
        super(MESSAGE);
    }
}