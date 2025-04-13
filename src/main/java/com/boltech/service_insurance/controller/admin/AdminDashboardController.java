package com.boltech.service_insurance.controller.admin;

import com.boltech.service_insurance.dto.CreateInsurancePolicyRequest;
import com.boltech.service_insurance.dto.UpdateInsurancePolicyRequest;
import com.boltech.service_insurance.model.InsurancePolicy;
import com.boltech.service_insurance.service.InsurancePolicyService;
import com.boltech.service_insurance.service.dashboard.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin")
@RestController
@RequestMapping("admin/dashboard")
public class AdminDashboardController {

    @Autowired
    @Qualifier("adminDashboard")
    DashboardService dashboardService;

    @Operation(operationId = "Policy Issued Data last 7 days", summary = "Policy Issued Data last 7 days")
    @GetMapping("/policies/issued")
    public ResponseEntity<?> policyIssuedData() {
        return ResponseEntity.ok().body(dashboardService.getPolicyIssuedData(7));
    }
}
