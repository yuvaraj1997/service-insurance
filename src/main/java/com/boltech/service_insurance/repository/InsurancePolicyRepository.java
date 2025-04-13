package com.boltech.service_insurance.repository;

import com.boltech.service_insurance.constant.InsurancePolicyConstants;
import com.boltech.service_insurance.model.InsurancePolicy;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InsurancePolicyRepository extends MongoRepository<InsurancePolicy, String> {

    List<InsurancePolicy> findAllByInsuranceTypeAndStatus(InsurancePolicyConstants.Type type, InsurancePolicyConstants.Status status);

    Optional<InsurancePolicy> findByName(String name);
}
