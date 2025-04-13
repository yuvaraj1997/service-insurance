package com.boltech.service_insurance.dto;

import com.boltech.service_insurance.dto.quote.QuoteDetails;
import com.boltech.service_insurance.factory.quote.dto.QuoteBreakdownResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserQuotePolicyResponse {

    private String userQuoteId;

    private Policy policy;

    private QuoteDetails details;

    private BigDecimal coverageAmount;

    private BigDecimal premiumPerMonth;

    private int termLengthInMonths;

    private QuoteBreakdownResponse quoteBreakdown;

    @Data
    static public class Policy {
        private String id;
        private String name;
        private String type;
    }

}
