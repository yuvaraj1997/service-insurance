package com.boltech.service_insurance.exception;

import com.boltech.service_insurance.constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @Autowired
    private Environment env;

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNAUTHORIZED, request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex, WebRequest request) {
        log.error("Application exception occurred: {} , detailed={}", ex.getMessage(), ex.getDetails());

        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        log.error("Unexpected error occurred: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, request.getDescription(false).replace("uri=", ""));

        // In production, you might want to hide detailed error messages
        if (!"prod".equals(env.getProperty("spring.profiles.active"))) {
            errorResponse.setMessage(ex.getMessage());
        }

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatus()));
    }

    // Handle validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<Object, Object> errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,           // Key: field name
                        FieldError::getDefaultMessage,  // Value: error message
                        (existing, replacement) -> existing // Merge function: keep first error if duplicate fields
                ));

        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_REQUEST, null, errorDetails, request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatus()));
    }
}
