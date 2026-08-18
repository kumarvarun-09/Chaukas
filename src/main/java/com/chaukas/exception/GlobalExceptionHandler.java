package com.chaukas.exception;

import com.chaukas.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chaukas.constants.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> userAlreadyExistsExceptionHandler(UserAlreadyExistsException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(exception.getMessage(),
                                USER_ALREADY_EXISTS.name(),
                                Instant.now(),
                                null
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, List<String>> details = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(e -> {
                            if (!details.containsKey(e.getField())) {
                                details.put(e.getField(), new ArrayList<>());
                            }
                            details.get(e.getField()).add(e.getDefaultMessage());
                        }
                );
        log.error("Validation failed", exception);
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("Validation failed",
                                VALIDATION_FAILED.name(),
                                Instant.now(),
                                details
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> runtimeExceptionHandler(Exception exception) {
        log.error("An unexpected error occurred", exception);
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse("An unexpected error occurred",
                                INTERNAL_SERVER_ERROR.name(),
                                Instant.now(),
                                null
                        )
                );
    }
}
