package com.boltech.service_insurance.service.dashboard;

import com.boltech.service_insurance.dto.dashboard.DashboardSummary;
import com.boltech.service_insurance.dto.dashboard.PolicyIssuedChart;

public interface DashboardService {

    DashboardSummary getSummary(String userId);

    PolicyIssuedChart getPolicyIssuedData(int lastHowManyDays);

}
