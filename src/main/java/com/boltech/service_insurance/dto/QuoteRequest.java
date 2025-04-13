package com.boltech.service_insurance.dto;

import com.boltech.service_insurance.dto.quote.AutoDetails;
import com.boltech.service_insurance.dto.quote.HomeDetails;
import com.boltech.service_insurance.dto.quote.LifeDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteRequest {

    @NotBlank(message = "Policy id is required")
    private String policyId;

    @NotBlank(message = "identification is required")
    String identification;

    private AutoDetails auto;
    private HomeDetails home;
    private LifeDetails life;

}
