package com.nhnacademy.twojopingfront.bookset.book.exception;

import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomApiException;

public class BookNotFoundException extends CustomApiException {
    public BookNotFoundException(ErrorResponseDto errorResponseDto) {
        super(errorResponseDto);
    }
}
