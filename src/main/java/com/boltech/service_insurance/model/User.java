package com.boltech.service_insurance.model;

import com.boltech.service_insurance.constant.UserConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document("users")
@Data
public class User {

    @Id
    private String id;

    private String email;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String password;

    private UserConstants.Status status;

    private List<UserConstants.Role> roles;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
