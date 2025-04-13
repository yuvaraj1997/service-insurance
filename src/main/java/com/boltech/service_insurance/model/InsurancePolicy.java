package com.boltech.service_insurance.model;

import com.boltech.service_insurance.constant.InsurancePolicyConstants;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document("insurance_policies")
@Data
public class InsurancePolicy {

    @Id
    private String id;

    private String name;

    private InsurancePolicyConstants.Type insuranceType;

    private BigDecimal coverageAmount;

    private BigDecimal premiumPerMonth;

    private int termLengthInMonths;

    private InsurancePolicyConstants.Status status;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @CreatedBy
    private String createdBy;

}
