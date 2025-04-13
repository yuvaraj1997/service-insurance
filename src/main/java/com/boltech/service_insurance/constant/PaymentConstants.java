package com.boltech.service_insurance.constant;

public final class PaymentConstants {
    private PaymentConstants() {
        // Prevent instantiation
    }

    public enum Status {
        SCHEDULED,
        COMPLETED,
        FAILED
    }
}
