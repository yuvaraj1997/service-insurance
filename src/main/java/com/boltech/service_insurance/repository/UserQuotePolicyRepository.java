package com.boltech.service_insurance.repository;

import com.boltech.service_insurance.constant.UserQuoteConstants;
import com.boltech.service_insurance.model.UserQuotePolicy;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserQuotePolicyRepository extends MongoRepository<UserQuotePolicy, String> {

    Optional<UserQuotePolicy> findByIdAndUserId(String id, String userId);

    int countByUserIdAndStatus(String userId, UserQuoteConstants.Status status);
}
