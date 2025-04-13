package com.boltech.service_insurance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Instant;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivatePolicyResponse {

    private String userPolicyId;

    private Instant nextPaymentDue;

}
