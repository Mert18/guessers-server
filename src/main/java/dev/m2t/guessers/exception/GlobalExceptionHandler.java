package dev.m2t.guessers.exception;

import dev.m2t.guessers.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceAlreadyExistsException.class})
    public <T extends RuntimeException> ResponseEntity<Object> handleBadRequestExceptions(T exception) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(exception.getMessage(), false, true));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(new BaseResponse<>("Fields validation failed", false, true, errors));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public <T extends RuntimeException> ResponseEntity<Object> handleEventNotExistsException(T exception) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(exception.getMessage(), false, true));
    }

}
