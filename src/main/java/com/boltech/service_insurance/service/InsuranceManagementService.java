package com.boltech.service_insurance.service;

import com.boltech.service_insurance.constant.*;
import com.boltech.service_insurance.dto.*;
import com.boltech.service_insurance.dto.quote.AutoDetails;
import com.boltech.service_insurance.dto.quote.HomeDetails;
import com.boltech.service_insurance.dto.quote.LifeDetails;
import com.boltech.service_insurance.dto.quote.QuoteDetails;
import com.boltech.service_insurance.exception.ApplicationException;
import com.boltech.service_insurance.factory.quote.QuoteCalculator;
import com.boltech.service_insurance.factory.quote.QuoteCalculatorFactory;
import com.boltech.service_insurance.factory.quote.dto.QuoteBreakdownResponse;
import com.boltech.service_insurance.model.InsurancePolicy;
import com.boltech.service_insurance.model.Payment;
import com.boltech.service_insurance.model.UserPolicy;
import com.boltech.service_insurance.model.UserQuotePolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.boltech.service_insurance.util.DateUtil.nowZoneDateTime;
import static com.boltech.service_insurance.util.DateUtil.zoneDateTime;

@Slf4j
@Service
public class InsuranceManagementService {

    @Autowired
    InsurancePolicyService insurancePolicyService;

    @Autowired
    UserQuotePolicyService userQuotePolicyService;

    @Autowired
    UserPolicyService userPolicyService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    QuoteCalculatorFactory quoteCalculatorFactory;

    QuoteBreakdownResponse getMonthlyPremium(QuoteRequest quoteRequest, InsurancePolicy insurancePolicy) {
        QuoteCalculator quoteCalculator = quoteCalculatorFactory.getQuoteCalculator(insurancePolicy.getInsuranceType());

        return quoteCalculator.calculateQuote(insurancePolicy, quoteRequest);
    }


    public QuoteResponse calculateQuote(QuoteRequest req) {
        InsurancePolicy insurancePolicy = insurancePolicyService.getActivePolicyById(req.getPolicyId());

        validateReq(req, insurancePolicy.getInsuranceType());

        return new QuoteResponse(getMonthlyPremium(req, insurancePolicy).getTotal());
    }

    public UserQuotePolicyResponse generateQuote(String userId, QuoteRequest req) {
        InsurancePolicy insurancePolicy = insurancePolicyService.getActivePolicyById(req.getPolicyId());

        validateReq(req, insurancePolicy.getInsuranceType());

        QuoteBreakdownResponse quoteBreakdownResponse = getMonthlyPremium(req, insurancePolicy);

        UserQuotePolicy userQuotePolicy = new UserQuotePolicy();
        userQuotePolicy.setUserId(userId);
        userQuotePolicy.setInsurancePolicyId(insurancePolicy.getId());
        userQuotePolicy.setCoverageAmount(insurancePolicy.getCoverageAmount());
        userQuotePolicy.setPremiumPerMonth(quoteBreakdownResponse.getTotal());
        userQuotePolicy.setQuoteBreakdownResponse(quoteBreakdownResponse);
        userQuotePolicy.setTermLengthInMonths(insurancePolicy.getTermLengthInMonths());
        userQuotePolicy.setStatus(UserQuoteConstants.Status.QUOTED);
        userQuotePolicy.setDetails(getDetailsBasedOnType(req, insurancePolicy.getInsuranceType()));
        
        userQuotePolicy = userQuotePolicyService.save(userQuotePolicy);

        return buildUserQuotePolicyResponse(insurancePolicy, userQuotePolicy);
    }

    public ActivatePolicyResponse activatePolicy(String userId, PaymentRequest req) {
        UserQuotePolicy userQuotePolicy = userQuotePolicyService.findActiveUserQuotePolicy(req.getUserQuotePolicyId(), userId);

        UserPolicy userPolicy = createUserPolicy(userQuotePolicy);

        createMonthlyPayments(userPolicy);

        userQuotePolicy.setStatus(UserQuoteConstants.Status.GENERATED);
        userQuotePolicyService.save(userQuotePolicy);

        ActivatePolicyResponse activatePolicyResponse = new ActivatePolicyResponse();
        activatePolicyResponse.setUserPolicyId(userPolicy.getId());
        activatePolicyResponse.setNextPaymentDue(paymentService.getNextPaymentDue(userPolicy.getId()).getBillingSchedule());
        return activatePolicyResponse;
    }

    private UserPolicy createUserPolicy(UserQuotePolicy userQuotePolicy) {
        UserPolicy userPolicy = new UserPolicy();

        userPolicy.setUserId(userQuotePolicy.getUserId());
        userPolicy.setInsurancePolicyId(userQuotePolicy.getInsurancePolicyId());
        userPolicy.setCoverageAmount(userQuotePolicy.getCoverageAmount());
        userPolicy.setPremiumPerMonth(userQuotePolicy.getPremiumPerMonth());
        userPolicy.setTermLengthInMonths(userQuotePolicy.getTermLengthInMonths());
        userPolicy.setQuoteBreakdown(userQuotePolicy.getQuoteBreakdownResponse());
        userPolicy.setStatus(UserPolicyConstants.Status.ACTIVE);
        userPolicy.setDetails(userQuotePolicy.getDetails());
        userPolicy.setUserQuotePolicyId(userQuotePolicy.getId());
        userPolicy.setFile(userQuotePolicy.getFile());

        return userPolicyService.save(userPolicy);
    }

    private void createMonthlyPayments(UserPolicy userPolicy) {
        ZonedDateTime now = nowZoneDateTime();

        for (int i=0;i<userPolicy.getTermLengthInMonths();i++) {
            Payment payment = new Payment();
            payment.setUserId(userPolicy.getUserId());
            payment.setUserPolicyId(userPolicy.getId());
            payment.setAmount(userPolicy.getPremiumPerMonth());
            payment.setMonth(i + 1);

            if (i == 0) {
                payment.setStatus(PaymentConstants.Status.COMPLETED);
                payment.setPaymentDate(now.toInstant());
            } else {
                payment.setStatus(PaymentConstants.Status.SCHEDULED);
                Instant billingSchedule = now
                                            .plusMonths(i)
                                            .withHour(0)
                                            .withMinute(0)
                                            .withSecond(0)
                                            .withNano(0)
                                            .toInstant();
                payment.setBillingSchedule(billingSchedule);
            }

            paymentService.save(payment);
        }
    }

    private UserQuotePolicyResponse buildUserQuotePolicyResponse(InsurancePolicy insurancePolicy, UserQuotePolicy userQuotePolicy) {
        UserQuotePolicyResponse userQuotePolicyResponse = new UserQuotePolicyResponse();

        userQuotePolicyResponse.setUserQuoteId(userQuotePolicy.getId());

        userQuotePolicyResponse.setPolicy(new UserQuotePolicyResponse.Policy());
        userQuotePolicyResponse.getPolicy().setId(insurancePolicy.getId());
        userQuotePolicyResponse.getPolicy().setName(insurancePolicy.getName());
        userQuotePolicyResponse.getPolicy().setType(insurancePolicy.getInsuranceType().name());

        userQuotePolicyResponse.setDetails(userQuotePolicy.getDetails());
        userQuotePolicyResponse.setCoverageAmount(userQuotePolicy.getCoverageAmount());
        userQuotePolicyResponse.setPremiumPerMonth(userQuotePolicy.getPremiumPerMonth());
        userQuotePolicyResponse.setTermLengthInMonths(userQuotePolicy.getTermLengthInMonths());

        userQuotePolicyResponse.setQuoteBreakdown(userQuotePolicy.getQuoteBreakdownResponse());

        return userQuotePolicyResponse;
    }

    private QuoteDetails getDetailsBasedOnType(QuoteRequest req, InsurancePolicyConstants.Type insuranceType) {
        switch (insuranceType) {
            case AUTO -> {
                return req.getAuto();
            }
            case LIFE -> {
                return req.getLife();
            }
            case HOME -> {
                return req.getHome();
            }
            default -> throw new RuntimeException("Insurance type return details is not handled insuranceType=" + insuranceType.name());
        }
    }

    private void validateReq(QuoteRequest req, InsurancePolicyConstants.Type insuranceType) {
        switch (insuranceType) {
            case AUTO -> validateAuto(req);
            case LIFE -> validateLife(req);
            case HOME -> validateHome(req);
            default -> throw new RuntimeException("Insurance type is not handled insuranceType=" + insuranceType.name());
        }
    }

    private void validateAuto(QuoteRequest req) {
        if (null == req.getAuto()) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Auto details are required");
        }

        AutoDetails auto = req.getAuto();

        if (null == auto.getType() || auto.getType().isBlank() || !List.of("car", "motor").contains(auto.getType())) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Invalid auto type, only allowed [car | motor]");
        }
    }

    private void validateHome(QuoteRequest req) {
        if (null == req.getHome()) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Home details are required");
        }

        HomeDetails home = req.getHome();

        if (home.getNumberOfRooms() < 1) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Minimum room should be 1");
        }

        if (null == home.getType() || home.getType().isBlank() || !List.of("Apartment", "Landed").contains(home.getType())) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Invalid home type, only allowed [apartment | landed]");
        }
    }

    private void validateLife(QuoteRequest req) {
        if (null == req.getLife()) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Life details are required");
        }

        LifeDetails life = req.getLife();

        if (life.getAge() < 18) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Minimum age should 18 for get life insurance policy");
        }

        if (null == life.getHealthStatus() || life.getHealthStatus().isBlank() || !List.of("Excellent", "Good", "Poor").contains(life.getHealthStatus())) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Invalid health status, only allowed [Excellent | Good | Poor]");
        }
    }

    public UserPoliciesResponse getAllUserPoliciesByUserId(String userId) {
        List<UserPolicy> userPolicyList = userPolicyService.findAllByUserId(userId);

        Map<String, InsurancePolicy> policyMap = new HashMap<>();

        UserPoliciesResponse userPoliciesResponse = new UserPoliciesResponse();

        userPoliciesResponse.setPolicies(userPolicyList.stream().map( userPolicy ->
                new UserPoliciesResponse.UserPolicyResponse(
                        userPolicy.getId(),
                        policyMap.computeIfAbsent(userPolicy.getInsurancePolicyId(), id -> insurancePolicyService.findById(id)).getName(),
                        policyMap.computeIfAbsent(userPolicy.getInsurancePolicyId(), id -> insurancePolicyService.findById(id)).getInsuranceType().name(),
                        userPolicy.getStatus().name(),
                        userPolicy.getCoverageAmount()
                )
        ).toList());

        return userPoliciesResponse;
    }

    public UserPolicyDetailResponse getUserPolicyDetailsByUserIdAndUserPolicyId(String userId, String userPolicyId) {
       UserPolicy userPolicy = userPolicyService.findByIdAndUserId(userPolicyId, userId);

        UserPolicyDetailResponse res = new UserPolicyDetailResponse();

        InsurancePolicy insurancePolicy = insurancePolicyService.findById(userPolicy.getInsurancePolicyId());
        res.setPolicy(new UserPolicyDetailResponse.UserInsurancePolicyDetail(
                insurancePolicy.getName(),
                insurancePolicy.getInsuranceType().name()
        ));

        Payment payment = paymentService.getNextPaymentDue(userPolicy.getId());

        Instant endDate = zoneDateTime(userPolicy.getCreatedAt()).plusMonths(userPolicy.getTermLengthInMonths()).toInstant();

        res.setUserPolicy(new UserPolicyDetailResponse.UserPolicyDetail(
                userPolicy.getId(),
                userPolicy.getStatus().name(),
                userPolicy.getCoverageAmount(),
                userPolicy.getPremiumPerMonth(),
                "Monthly(M)",
                payment.getBillingSchedule(),
                userPolicy.getTermLengthInMonths(),
                userPolicy.getCreatedAt(),
                endDate,
                userPolicy.getTermLengthInMonths() - payment.getMonth() + 1,
                userPolicy.getFile() != null
        ));

        res.setQuoteBreakdown(userPolicy.getQuoteBreakdown());

        return res;
    }

    public List<Payment> getUserPolicyPaymentListByUserIdAndUserPolicyId(String userId, String userPolicyId) {
        UserPolicy userPolicy = userPolicyService.findByIdAndUserId(userPolicyId, userId);

        return paymentService.getAllPaymentsByUserPolicyIdSortedByScheduledDate(userPolicyId);
    }

    public void uploadFile(String userQuotePolicyId, String userId, MultipartFile file) throws IOException {
        UserQuotePolicy userQuotePolicy = userQuotePolicyService.findActiveUserQuotePolicy(userQuotePolicyId, userId);

        userQuotePolicy.setFile(file.getBytes());

        userQuotePolicyService.save(userQuotePolicy);
    }

    public byte[] downloadFile(String userId, String userPolicyId) {
        UserPolicy userPolicy = userPolicyService.findByIdAndUserId(userPolicyId, userId);

        if (userPolicy.getFile() == null) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST);
        }

        return userPolicy.getFile();
    }
}
