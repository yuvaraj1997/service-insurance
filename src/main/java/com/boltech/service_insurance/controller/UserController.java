package com.boltech.service_insurance.controller;

import com.boltech.service_insurance.dto.UserPoliciesResponse;
import com.boltech.service_insurance.dto.UserPolicyDetailResponse;
import com.boltech.service_insurance.model.Payment;
import com.boltech.service_insurance.security.dto.AuthorizationToken;
import com.boltech.service_insurance.service.InsuranceManagementService;
import com.boltech.service_insurance.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "User")
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    InsuranceManagementService insuranceManagementService;

    @Operation(operationId = "Get Own Profile", summary = "Get Own Profile")
    @GetMapping("/profile")
    public ResponseEntity<?> getOwnProfile(@AuthenticationPrincipal AuthorizationToken authorizationToken) {
        return ResponseEntity.ok().body(userService.getById(authorizationToken.getUserId()));
    }

    @Operation(operationId = "Get Own User Policies", summary = "Get Own User Policies")
    @GetMapping("/policies")
    public ResponseEntity<UserPoliciesResponse> getOwnPolicies(@AuthenticationPrincipal AuthorizationToken authorizationToken) {
        return ResponseEntity.ok().body(insuranceManagementService.getAllUserPoliciesByUserId(authorizationToken.getUserId()));
    }

    @Operation(operationId = "Get Own User Policy Details", summary = "Get Own User Policy Details")
    @GetMapping("/policies/{userPolicyId}")
    public ResponseEntity<UserPolicyDetailResponse> getOwnUserPolicyDetails(@AuthenticationPrincipal AuthorizationToken authorizationToken, @PathVariable String userPolicyId) {
        return ResponseEntity.ok().body(insuranceManagementService.getUserPolicyDetailsByUserIdAndUserPolicyId(authorizationToken.getUserId(), userPolicyId));
    }

    @Operation(operationId = "Download File", summary = "Download file")
    @GetMapping("/policies/{userPolicyId}/download")
    public ResponseEntity<?> downloadFile(@AuthenticationPrincipal AuthorizationToken authorizationToken, @PathVariable String userPolicyId) {
        byte[] file = insuranceManagementService.downloadFile(authorizationToken.getUserId(), userPolicyId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);

    }

    @Operation(operationId = "Get Own User Policy Payment Details", summary = "Get Own User Policy Payment Details")
    @GetMapping("/policies/{userPolicyId}/payments")
    public ResponseEntity<List<Payment>> getOwnUserPolicyPaymentList(@AuthenticationPrincipal AuthorizationToken authorizationToken, @PathVariable String userPolicyId) {
        return ResponseEntity.ok().body(insuranceManagementService.getUserPolicyPaymentListByUserIdAndUserPolicyId(authorizationToken.getUserId(), userPolicyId));
    }
}
