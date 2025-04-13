package com.boltech.service_insurance.model;

import com.boltech.service_insurance.constant.UserPolicyConstants;
import com.boltech.service_insurance.dto.quote.QuoteDetails;
import com.boltech.service_insurance.factory.quote.dto.QuoteBreakdownResponse;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document("user_policies")
@Data
public class UserPolicy {

    @Id
    private String id;

    private String userId;

    private String insurancePolicyId;

    private BigDecimal coverageAmount;

    private BigDecimal premiumPerMonth;

    private int termLengthInMonths;

    private UserPolicyConstants.Status status;

    private QuoteDetails details;

    private QuoteBreakdownResponse quoteBreakdown;

    private String userQuotePolicyId;

    private byte[] file;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @CreatedBy
    private String createdBy;

}
