package com.boltech.service_insurance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPoliciesResponse {

    private List<UserPolicyResponse> policies;

    @Data
    @AllArgsConstructor
    public static class UserPolicyResponse {
        String userPolicyId;
        String name;
        String type;
        String status;
        BigDecimal coverageAmount;
    }

}
