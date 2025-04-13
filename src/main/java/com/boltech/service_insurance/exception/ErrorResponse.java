package com.boltech.service_insurance.exception;

import com.boltech.service_insurance.constant.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

import static com.boltech.service_insurance.util.DateUtil.nowAsString;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private Integer status;
    private String code;
    private String message;
    private String details;
    private Map<Object, Object> additionalProperties;
    private String timestamp;
    private String path;

    public ErrorResponse(ErrorCode errorCode, String details, Map<Object, Object> additionalProperties, String path) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.details = details;
        this.additionalProperties = additionalProperties;
        this.timestamp = nowAsString();
        this.path = path;
    }

    public ErrorResponse(ErrorCode errorCode, String path) {
        this(errorCode, null, null, path);
    }
}