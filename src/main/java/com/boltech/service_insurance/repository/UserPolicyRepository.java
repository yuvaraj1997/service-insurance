package com.boltech.service_insurance.repository;

import com.boltech.service_insurance.constant.UserPolicyConstants;
import com.boltech.service_insurance.model.UserPolicy;
import com.boltech.service_insurance.repository.custom.CustomUserPolicyRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserPolicyRepository extends MongoRepository<UserPolicy, String>, CustomUserPolicyRepository {

    List<UserPolicy> findAllByUserId(String userId);

    Optional<UserPolicy> findByIdAndUserId(String id, String userId);

    int countByUserIdAndStatus(String userId, UserPolicyConstants.Status status);
}
