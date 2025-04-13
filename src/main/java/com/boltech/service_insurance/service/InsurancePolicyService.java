package com.boltech.service_insurance.service;

import com.boltech.service_insurance.constant.ErrorCode;
import com.boltech.service_insurance.constant.InsurancePolicyConstants;
import com.boltech.service_insurance.dto.CreateInsurancePolicyRequest;
import com.boltech.service_insurance.dto.InsurancePolicyCustomerResponse;
import com.boltech.service_insurance.dto.UpdateInsurancePolicyRequest;
import com.boltech.service_insurance.exception.ApplicationException;
import com.boltech.service_insurance.model.InsurancePolicy;
import com.boltech.service_insurance.repository.InsurancePolicyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InsurancePolicyService {

    @Autowired
    InsurancePolicyRepository insurancePolicyRepository;

    public InsurancePolicy save(InsurancePolicy insurancePolicy) {
        return insurancePolicyRepository.save(insurancePolicy);
    }

    public InsurancePolicy findById(String id) {
        return insurancePolicyRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.POLICY_NOT_FOUND));
    }

    public Optional<InsurancePolicy> findByName(String name) {
        return insurancePolicyRepository.findByName(name);
    }

    public void delete(String id) {
        InsurancePolicy insurancePolicy = findById(id);

        insurancePolicyRepository.delete(insurancePolicy);
    }

    public InsurancePolicy getActivePolicyById(String policyId) {
        InsurancePolicy insurancePolicy = findById(policyId);

        if (insurancePolicy.getStatus() != InsurancePolicyConstants.Status.ACTIVE) {
            log.warn("Policy is not active [{}]", policyId);
            throw new ApplicationException(ErrorCode.POLICY_NOT_FOUND);
        }

        return insurancePolicy;
    }

    private InsurancePolicy mapRequestToPolicy(InsurancePolicy insurancePolicy, CreateInsurancePolicyRequest req) {
        insurancePolicy.setName(req.getName());
        insurancePolicy.setInsuranceType(InsurancePolicyConstants.Type.valueOf(req.getInsuranceType()));
        insurancePolicy.setCoverageAmount(req.getCoverageAmount());
        insurancePolicy.setPremiumPerMonth(req.getPremiumPerMonth());
        insurancePolicy.setTermLengthInMonths(req.getTermLengthInMonths());
        insurancePolicy.setStatus(InsurancePolicyConstants.Status.valueOf(req.getStatus()));
        return insurancePolicy;
    }

    public InsurancePolicy create(CreateInsurancePolicyRequest req) {
        InsurancePolicy insurancePolicy = new InsurancePolicy();

        return save(mapRequestToPolicy(insurancePolicy, req));
    }

    public InsurancePolicy update(UpdateInsurancePolicyRequest req) {
        InsurancePolicy insurancePolicy = findById(req.getId());

        return save(mapRequestToPolicy(insurancePolicy, req));
    }

    public InsurancePolicyCustomerResponse getPoliciesByType(InsurancePolicyConstants.Type type) {
        List<InsurancePolicy> insurancePolicies = insurancePolicyRepository.findAllByInsuranceTypeAndStatus(type, InsurancePolicyConstants.Status.ACTIVE);

        InsurancePolicyCustomerResponse res = new InsurancePolicyCustomerResponse();

        res.setPolicies(insurancePolicies.stream().map((insurancePolicy -> new InsurancePolicyCustomerResponse.InsurancePolicyCustDetail(
                insurancePolicy.getId(),
                insurancePolicy.getName(),
                insurancePolicy.getInsuranceType().name(),
                insurancePolicy.getCoverageAmount(),
                insurancePolicy.getPremiumPerMonth(),
                insurancePolicy.getTermLengthInMonths()
        ))).toList());

        return res;
    }
}
