package com.boltech.service_insurance.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class PolicyIssuedChart {

    private List<DailyCount> data;

}
