package dev.m2t.guessers.exception;

import dev.m2t.guessers.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

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

    @ExceptionHandler(UserAlreadyInvitedException.class)
    public <T extends RuntimeException> ResponseEntity<Object> handleUserAlreadyInvitedException(T exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse<>(exception.getMessage(), false, true));
    }

}
