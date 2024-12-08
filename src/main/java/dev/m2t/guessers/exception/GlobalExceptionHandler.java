package dev.m2t.guessers.exception;

import dev.m2t.guessers.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceAlreadyExistsException.class})
    public <T extends RuntimeException> ResponseEntity<Object> handleBadRequestExceptions(T exception) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(exception.getMessage(), false, true));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        for(ObjectError oe: errors) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(oe.getDefaultMessage(), false, true));
        }
        return ResponseEntity.badRequest().body(new BaseResponse<>("Field validation error.", false, true));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public <T extends RuntimeException> ResponseEntity<Object> handleEventNotExistsException(T exception) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(exception.getMessage(), false, true));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public <T extends RuntimeException> ResponseEntity<Object> handleUnauthorizedException(T exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse<>(exception.getMessage(), false, true));
    }

}
