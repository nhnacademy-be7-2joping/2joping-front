package com.nhnacademy.twojopingfront.common.error.exception.image;

public class ImageUploadException extends RuntimeException {
    public static final String MESSAGE = "이미지 업로드 중 오류가 발생했습니다.";
    public ImageUploadException() {
        super(MESSAGE);
    }
}