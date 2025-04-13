package com.boltech.service_insurance.exception;

import com.boltech.service_insurance.constant.ErrorCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String details;

    public ApplicationException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public ApplicationException(ErrorCode errorCode, String details) {
        this(errorCode, details, null);
    }

    public ApplicationException(ErrorCode errorCode, String details, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.details = details;
    }

}