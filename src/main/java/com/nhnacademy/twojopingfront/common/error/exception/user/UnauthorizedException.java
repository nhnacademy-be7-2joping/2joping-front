package com.nhnacademy.twojopingfront.common.error.exception.user;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Unauthorized");
    }
}
