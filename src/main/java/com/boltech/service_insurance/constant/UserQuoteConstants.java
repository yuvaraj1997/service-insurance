package com.boltech.service_insurance.constant;

public final class UserQuoteConstants {
    private UserQuoteConstants() {
        // Prevent instantiation
    }

    public enum Status {
        QUOTED,
        GENERATED,
        EXPIRED
    }
}
