package com.boltech.service_insurance.dto.quote;

import lombok.Data;

@Data
public class LifeDetails implements QuoteDetails {

    int age;

    String healthStatus;

    private byte[] medicalReport;

}
