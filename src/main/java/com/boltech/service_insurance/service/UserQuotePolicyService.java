package com.boltech.service_insurance.service;

import com.boltech.service_insurance.constant.ErrorCode;
import com.boltech.service_insurance.constant.UserQuoteConstants;
import com.boltech.service_insurance.exception.ApplicationException;
import com.boltech.service_insurance.model.UserQuotePolicy;
import com.boltech.service_insurance.repository.UserQuotePolicyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserQuotePolicyService {

    @Autowired
    UserQuotePolicyRepository userQuotePolicyRepository;

    public UserQuotePolicy findByIdAndUserId(String id, String userId) {
        return userQuotePolicyRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_QUOTE_POLICY_NOT_FOUND));
    }

    public UserQuotePolicy save(UserQuotePolicy userQuotePolicy) {
        return userQuotePolicyRepository.save(userQuotePolicy);
    }

    public UserQuotePolicy findActiveUserQuotePolicy(String userQuotePolicyId, String userId) {
        UserQuotePolicy userQuotePolicy = findByIdAndUserId(userQuotePolicyId, userId);

        if (userQuotePolicy.getStatus() == UserQuoteConstants.Status.GENERATED) {
            throw new ApplicationException(ErrorCode.USER_QUOTE_POLICY_GENERATED);
        }

        if (userQuotePolicy.getStatus() == UserQuoteConstants.Status.EXPIRED) {
            throw new ApplicationException(ErrorCode.USER_QUOTE_POLICY_EXPIRED);
        }

        return userQuotePolicy;
    }

    public int getPendingApplicationCount(String userId) {
        return userQuotePolicyRepository.countByUserIdAndStatus(userId, UserQuoteConstants.Status.QUOTED);
    }
}
