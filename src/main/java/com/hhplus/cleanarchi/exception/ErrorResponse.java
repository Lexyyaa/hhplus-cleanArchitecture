package com.hhplus.cleanarchi.exception;

public record ErrorResponse(
        String code,
        String message
) {}
