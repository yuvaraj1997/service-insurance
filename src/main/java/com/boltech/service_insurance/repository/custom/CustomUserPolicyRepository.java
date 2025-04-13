package com.boltech.service_insurance.repository.custom;

import com.boltech.service_insurance.dto.dashboard.DailyCount;

import java.util.List;

public interface CustomUserPolicyRepository {

    List<DailyCount> getUserPolicyCount(int numberOfDays);
}
