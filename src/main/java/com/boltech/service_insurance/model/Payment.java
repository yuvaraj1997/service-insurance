package com.boltech.service_insurance.model;

import com.boltech.service_insurance.constant.PaymentConstants;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document("payments")
@Data
public class Payment {

    @Id
    private String id;

    private String userId;

    private String userPolicyId;

    private BigDecimal amount;

    private PaymentConstants.Status status;

    private Instant paymentDate;

    private Instant billingSchedule;

    private Integer month;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
