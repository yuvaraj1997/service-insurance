package com.boltech.service_insurance.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //General Error
    INTERNAL_SERVER_ERROR("SYS_001", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error"),
    INVALID_REQUEST("SYS_002", HttpStatus.BAD_REQUEST.value(), "Invalid request"),
    UNAUTHORIZED("SYS_003", HttpStatus.UNAUTHORIZED.value(), "Unauthorized access"),
    FORBIDDEN("SYS_004", HttpStatus.FORBIDDEN.value(), "Access Denied"),

    // Authentication errors
    INVALID_CREDENTIALS("AUTH_001", HttpStatus.BAD_REQUEST.value(), "Invalid username or password"),

    // User errors
    USER_NOT_FOUND("USR_001", HttpStatus.BAD_REQUEST.value(), "User not found"),
    USER_ALREADY_EXISTS("USR_002", HttpStatus.BAD_REQUEST.value(), "User already exists"),

    // User errors
    POLICY_NOT_FOUND("PLY_001", HttpStatus.BAD_REQUEST.value(), "Policy not found"),
    POLICY_NOT_ACTIVE("PLY_002", HttpStatus.BAD_REQUEST.value(), "Policy not found"),

    //User Quote Policy
    USER_QUOTE_POLICY_NOT_FOUND("USR_QUOTE_PLY_001", HttpStatus.BAD_REQUEST.value(), "User's quote policy not found"),
    USER_QUOTE_POLICY_GENERATED("USR_QUOTE_PLY_002", HttpStatus.BAD_REQUEST.value(), "User's quote policy generated"),
    USER_QUOTE_POLICY_EXPIRED("USR_QUOTE_PLY_003", HttpStatus.BAD_REQUEST.value(), "User's quote policy expired"),

    USER_POLICY_NOT_FOUND("USR_PLY_001", HttpStatus.BAD_REQUEST.value(), "User's policy not found");

    private final String code;
    private final Integer status;
    private final String message;

}
