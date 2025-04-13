package com.boltech.service_insurance.config;

import com.boltech.service_insurance.constant.InsurancePolicyConstants;
import com.boltech.service_insurance.constant.UserConstants;
import com.boltech.service_insurance.dto.SignUpUserRequest;
import com.boltech.service_insurance.model.InsurancePolicy;
import com.boltech.service_insurance.model.User;
import com.boltech.service_insurance.service.InsurancePolicyService;
import com.boltech.service_insurance.service.UserManagementService;
import com.boltech.service_insurance.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class UserDataInitializer {

    private final String EMAIL = "admin@example.com";
    private final String PASSWORD = "Abcd@1234";

    @Bean
    CommandLineRunner initDefaultUsers(UserManagementService userManagementService, UserService userService, InsurancePolicyService insurancePolicyService) {
        return args -> {
            if (userService.findByEmail(EMAIL).isEmpty()) {
                SignUpUserRequest signUpUserRequest = new SignUpUserRequest();
                signUpUserRequest.setEmail(EMAIL);
                signUpUserRequest.setFirstName("Admin");
                signUpUserRequest.setLastName("User");
                signUpUserRequest.setPassword(PASSWORD);
                userManagementService.signUp(signUpUserRequest);

                User newUser = userService.findByEmail(EMAIL).get();
                newUser.getRoles().add(UserConstants.Role.ADMIN);

                userService.save(newUser);
            }

            if (insurancePolicyService.findByName("Life Insurance Max").isEmpty()) {
                InsurancePolicy insurancePolicy = new InsurancePolicy();
                insurancePolicy.setName("Life Insurance Max");
                insurancePolicy.setInsuranceType(InsurancePolicyConstants.Type.LIFE);
                insurancePolicy.setCoverageAmount(new BigDecimal(500000));
                insurancePolicy.setPremiumPerMonth(new BigDecimal(250));
                insurancePolicy.setTermLengthInMonths(12);
                insurancePolicy.setStatus(InsurancePolicyConstants.Status.ACTIVE);
                insurancePolicyService.save(insurancePolicy);
            }

            if (insurancePolicyService.findByName("Home Insurance Max").isEmpty()) {
                InsurancePolicy insurancePolicy = new InsurancePolicy();
                insurancePolicy.setName("Home Insurance Max");
                insurancePolicy.setInsuranceType(InsurancePolicyConstants.Type.HOME);
                insurancePolicy.setCoverageAmount(new BigDecimal(1000000));
                insurancePolicy.setPremiumPerMonth(new BigDecimal(500));
                insurancePolicy.setTermLengthInMonths(12);
                insurancePolicy.setStatus(InsurancePolicyConstants.Status.ACTIVE);
                insurancePolicyService.save(insurancePolicy);
            }

            if (insurancePolicyService.findByName("Auto Insurance Max").isEmpty()) {
                InsurancePolicy insurancePolicy = new InsurancePolicy();
                insurancePolicy.setName("Auto Insurance Max");
                insurancePolicy.setInsuranceType(InsurancePolicyConstants.Type.AUTO);
                insurancePolicy.setCoverageAmount(new BigDecimal(20000));
                insurancePolicy.setPremiumPerMonth(new BigDecimal(100));
                insurancePolicy.setTermLengthInMonths(12);
                insurancePolicy.setStatus(InsurancePolicyConstants.Status.ACTIVE);
                insurancePolicyService.save(insurancePolicy);
            }
        };
    }
}