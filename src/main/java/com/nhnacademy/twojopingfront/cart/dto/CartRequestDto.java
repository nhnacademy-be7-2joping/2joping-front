package com.nhnacademy.twojopingfront.cart.dto;

import jakarta.validation.constraints.NotNull;

public record CartRequestDto (@NotNull(message = "필수") long bookId, @NotNull(message = "필수") int quantity) {
}
