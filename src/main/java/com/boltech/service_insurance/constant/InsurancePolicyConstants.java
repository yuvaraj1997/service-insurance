package com.boltech.service_insurance.constant;

public final class InsurancePolicyConstants {
    private InsurancePolicyConstants() {
        // Prevent instantiation
    }

    public enum Type {
        AUTO,
        HOME,
        LIFE
    }

    public enum Status {
        ACTIVE, PENDING, EXPIRED
    }
}
