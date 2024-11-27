package com.nhnacademy.twojopingfront.common.error.exception.jwt;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
