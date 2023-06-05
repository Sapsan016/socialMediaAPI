package ru.gorbunov.social_media_api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String status;
    private final String reason;
    private final String message;
    private final String timestamp;


}
