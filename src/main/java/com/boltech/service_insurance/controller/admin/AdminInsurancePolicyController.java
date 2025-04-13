package com.boltech.service_insurance.controller.admin;

import com.boltech.service_insurance.dto.CreateInsurancePolicyRequest;
import com.boltech.service_insurance.dto.UpdateInsurancePolicyRequest;
import com.boltech.service_insurance.model.InsurancePolicy;
import com.boltech.service_insurance.service.InsurancePolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin")
@RestController
@RequestMapping("admin/insurance-policies")
public class AdminInsurancePolicyController {

    @Autowired
    InsurancePolicyService insurancePolicyService;

    @Operation(operationId = "Create Insurance Policy", summary = "Create Insurance Policy")
    @PostMapping("")
    public ResponseEntity<InsurancePolicy> create(@RequestBody @Valid CreateInsurancePolicyRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(insurancePolicyService.create(req));
    }

    @Operation(operationId = "Find Insurance Policy", summary = "Find Insurance Policy")
    @GetMapping("/{id}")
    public ResponseEntity<InsurancePolicy> findById(@PathVariable String id) {
        return ResponseEntity.ok(insurancePolicyService.findById(id));
    }

    @Operation(operationId = "Update Insurance Policy", summary = "Update Insurance Policy")
    @PutMapping("")
    public ResponseEntity<InsurancePolicy> update(@RequestBody @Valid UpdateInsurancePolicyRequest req) {
        return ResponseEntity.ok(insurancePolicyService.update(req));
    }

    @Operation(operationId = "Delete Insurance Policy", summary = "Delete Insurance Policy")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        insurancePolicyService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
