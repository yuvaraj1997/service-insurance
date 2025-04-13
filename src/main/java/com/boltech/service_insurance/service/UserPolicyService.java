package com.boltech.service_insurance.service;

import com.boltech.service_insurance.constant.ErrorCode;
import com.boltech.service_insurance.constant.UserPolicyConstants;
import com.boltech.service_insurance.dto.dashboard.DailyCount;
import com.boltech.service_insurance.exception.ApplicationException;
import com.boltech.service_insurance.model.UserPolicy;
import com.boltech.service_insurance.repository.UserPolicyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserPolicyService {

    @Autowired
    UserPolicyRepository userPolicyRepository;

    public UserPolicy save(UserPolicy userPolicy) {
        return userPolicyRepository.save(userPolicy);
    }

    public List<UserPolicy> findAllByUserId(String userId) {
        return userPolicyRepository.findAllByUserId(userId);
    }

    public UserPolicy findByIdAndUserId(String id, String userId) {
        return userPolicyRepository.findByIdAndUserId(id, userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_POLICY_NOT_FOUND)
        );
    }

    public int getActivePolicyCount(String userId) {
       return userPolicyRepository.countByUserIdAndStatus(userId, UserPolicyConstants.Status.ACTIVE);
    }

    public List<DailyCount> getUserPolicyCount(int numberOfDays) {
        return userPolicyRepository.getUserPolicyCount(numberOfDays);
    }
}
