package com.boltech.service_insurance.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("user_sessions")
@Data
public class UserSession {

    @Id
    private String id;

    private String userId;

    private Boolean isActive;

    private Instant expiredAt;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
