package com.boltech.service_insurance.controller;

import com.boltech.service_insurance.constant.InsurancePolicyConstants;
import com.boltech.service_insurance.dto.*;
import com.boltech.service_insurance.security.dto.AuthorizationToken;
import com.boltech.service_insurance.service.InsuranceManagementService;
import com.boltech.service_insurance.service.InsurancePolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Insurance")
@RestController
@RequestMapping("insurance")
public class InsuranceController {

    @Autowired
    InsuranceManagementService insuranceManagementService;

    @Autowired
    InsurancePolicyService insurancePolicyService;

    @Operation(operationId = "Get all policies by type", summary = "Get all policies by type")
    @GetMapping("/policies")
    public ResponseEntity<InsurancePolicyCustomerResponse> getPoliciesByType(@RequestParam InsurancePolicyConstants.Type type) {
        return ResponseEntity.ok().body(insurancePolicyService.getPoliciesByType(type));
    }

    @Operation(operationId = "Calculate Quote Only", summary = "Calculate Quote Only")
    @PostMapping("/calculate-quote")
    public ResponseEntity<QuoteResponse> calculateQuote(@RequestBody @Valid QuoteRequest req) {
        return ResponseEntity.ok().body(insuranceManagementService.calculateQuote(req));
    }

    @Operation(operationId = "Generate Quote", summary = "Generate Quote")
    @PostMapping("/generate-quote")
    public ResponseEntity<UserQuotePolicyResponse> generateQuote(@AuthenticationPrincipal AuthorizationToken authorizationToken, @RequestBody @Valid QuoteRequest req) {
        return ResponseEntity.ok().body(insuranceManagementService.generateQuote(authorizationToken.getUserId(), req));
    }

    @Operation(operationId = "Generate Quote", summary = "Generate Quote")
    @PostMapping("/upload")
    public ResponseEntity<?> generateQuoteWithFile(@AuthenticationPrincipal AuthorizationToken authorizationToken, @RequestPart("userQuotePolicyId") String userQuotePolicyId, @RequestPart("file") MultipartFile file) throws IOException {
        insuranceManagementService.uploadFile(userQuotePolicyId, authorizationToken.getUserId(), file);
        return ResponseEntity.ok().build();
    }

    @Operation(operationId = "Payment / Activate Policy", summary = "Payment / Activate Policy")
    @PostMapping("/payment")
    public ResponseEntity<ActivatePolicyResponse> activatePolicy(@AuthenticationPrincipal AuthorizationToken authorizationToken, @RequestBody @Valid PaymentRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(insuranceManagementService.activatePolicy(authorizationToken.getUserId(), req));
    }

}
