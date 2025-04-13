package com.boltech.service_insurance.dto;

import com.boltech.service_insurance.constant.InsurancePolicyConstants;
import com.boltech.service_insurance.validators.EnumValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateInsurancePolicyRequest {

    @NotBlank(message = "Policy name is required")
    private String name;

    @NotBlank(message = "Insurance type is required")
    @EnumValue(enumClass = InsurancePolicyConstants.Type.class, message = "Invalid insurance type")
    private String insuranceType;

    @NotNull(message = "Coverage amount is required")
    @Positive(message = "Coverage must be greater than 0")
    private BigDecimal coverageAmount;

    @NotNull(message = "Premium per month is required")
    @Positive(message = "Premium must be greater than 0")
    private BigDecimal premiumPerMonth;

    @NotNull(message = "Term length in months is required")
    @Positive(message = "Term must be at least 1 month")
    private int termLengthInMonths;

    @NotBlank(message = "Status is required")
    @EnumValue(enumClass = InsurancePolicyConstants.Status.class, message = "Invalid status")
    private String status;
}
