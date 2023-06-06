package ru.gorbunov.social_media_api.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.gorbunov.social_media_api.security.jwt.JwtAuthenticationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class ErrorHandler {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse objectNotFoundExceptionResponse(final ObjectNotFoundException e) {
        return new ErrorResponse(e.getMessage(),
                "NOT_FOUND", "Запрашиваемый объект не найден.", LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(final MethodArgumentNotValidException e) {
        return new ErrorResponse(e.getMessage(),
                "BAD_REQUEST", "Неверный запрос.", LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badArgumentRequest(final MethodArgumentTypeMismatchException e) {
        return new ErrorResponse(e.getMessage(),
                "BAD_REQUEST", "Неверный запрос.", LocalDateTime.now().format(FORMATTER));
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badArgumentRequest(final IllegalArgumentException e) {
        return new ErrorResponse(e.getMessage(),
                "BAD_REQUEST", "Неверный запрос.", LocalDateTime.now().format(FORMATTER));
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse JwtAuthenticationException(final JwtAuthenticationException e) {
        return new ErrorResponse(e.getMessage(),
                "FORBIDDEN", "JWT token is expired or invalid",
                LocalDateTime.now().format(FORMATTER));
    }
}

