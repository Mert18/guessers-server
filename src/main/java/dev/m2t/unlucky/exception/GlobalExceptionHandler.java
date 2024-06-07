package dev.m2t.unlucky.exception;

import dev.m2t.unlucky.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UsernameAlreadyExistsException.class, UsernameNotFoundException.class})
    public <T extends RuntimeException> ResponseEntity<Object> handleBadRequestExceptions(T exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<>(exception.getMessage(), false, true));
    }
}
