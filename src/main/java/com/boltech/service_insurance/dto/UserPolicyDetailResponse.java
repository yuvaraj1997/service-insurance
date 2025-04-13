package com.boltech.service_insurance.dto;

import com.boltech.service_insurance.factory.quote.dto.QuoteBreakdownResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPolicyDetailResponse {

    private UserInsurancePolicyDetail policy;

    private UserPolicyDetail userPolicy;

    private QuoteBreakdownResponse quoteBreakdown;


    @Data
    @AllArgsConstructor
    public static class UserInsurancePolicyDetail {
        String name;
        String type;
    }

    @Data
    @AllArgsConstructor
    public static class UserPolicyDetail {
        String userPolicyId;
        String status;
        BigDecimal coverageAmount;
        BigDecimal monthlyPremiumAmount;
        String paymentMode;
        Instant paymentDueDate;
        int termInMonths;
        Instant startDate;
        Instant endDate;
        int termRemainingInMonths;
        boolean haveDocument;
    }

}
