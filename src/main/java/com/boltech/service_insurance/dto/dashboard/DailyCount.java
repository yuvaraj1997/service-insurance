package com.boltech.service_insurance.dto.dashboard;

import lombok.Data;

@Data
public class DailyCount {
    private String date;
    private int policiesIssued;
}
