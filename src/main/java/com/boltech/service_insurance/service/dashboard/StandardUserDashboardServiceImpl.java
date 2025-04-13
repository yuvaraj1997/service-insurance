package com.boltech.service_insurance.service.dashboard;

import com.boltech.service_insurance.dto.dashboard.DashboardSummary;
import com.boltech.service_insurance.dto.dashboard.PolicyIssuedChart;
import com.boltech.service_insurance.service.UserPolicyService;
import com.boltech.service_insurance.service.UserQuotePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class StandardUserDashboardServiceImpl implements DashboardService {

    @Autowired
    UserPolicyService userPolicyService;

    @Autowired
    UserQuotePolicyService userQuotePolicyService;

    @Override
    public DashboardSummary getSummary(String userId) {
        DashboardSummary dashboardSummary = new DashboardSummary();

        dashboardSummary.setActivePolicies(userPolicyService.getActivePolicyCount(userId));
        dashboardSummary.setPendingApplications(userQuotePolicyService.getPendingApplicationCount(userId));

        return dashboardSummary;
    }

    @Override
    public PolicyIssuedChart getPolicyIssuedData(int lastHowManyDays) {
        //TODO: Make is abstract
        throw new RuntimeException("");
    }
}
