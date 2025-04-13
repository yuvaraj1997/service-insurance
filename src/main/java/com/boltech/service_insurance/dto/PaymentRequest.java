package com.boltech.service_insurance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequest {

    @NotBlank(message = "User Quote Policy id is required")
    private String userQuotePolicyId;

}
