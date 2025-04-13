package com.boltech.service_insurance.controller;

import com.boltech.service_insurance.dto.dashboard.DashboardSummary;
import com.boltech.service_insurance.security.dto.AuthorizationToken;
import com.boltech.service_insurance.service.dashboard.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Dashboard")
@RestController
@RequestMapping("dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @Operation(operationId = "Summary", summary = "Summary")
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummary> getSummary(@AuthenticationPrincipal AuthorizationToken authorizationToken) {
        return ResponseEntity.ok().body(dashboardService.getSummary(authorizationToken.getUserId()));
    }
}
