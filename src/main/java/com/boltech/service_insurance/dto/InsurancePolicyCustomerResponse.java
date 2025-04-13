package com.boltech.service_insurance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsurancePolicyCustomerResponse {

    private List<InsurancePolicyCustDetail> policies;

    @Data
    @AllArgsConstructor
    static public class InsurancePolicyCustDetail {
        private String id;
        private String name;
        private String type;
        private BigDecimal coverageAmount;
        private BigDecimal premiumPerMonth;
        private int termLengthInMonths;
    }

}
