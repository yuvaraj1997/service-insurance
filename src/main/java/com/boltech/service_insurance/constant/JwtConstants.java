package com.boltech.service_insurance.constant;

import lombok.Getter;

public final class JwtConstants {
    private JwtConstants() {
        // Prevent instantiation
    }

    public static final String ISSUER = "insurance";
    public static final String CLAIMS_ROLES = "roles";

    @Getter
    public enum TokenType {
        REFRESH_TOKEN("REFRESH_TOKEN", (long) (60 * 60)), //15 minutes
        ACCESS_TOKEN("ACCESS_TOKEN", (long) 60 * 30); //5 minutes

        final String type;
        final Long expirationTimeInSeconds;

        TokenType(String type, Long expirationTimeInSeconds) {
            this.type = type;
            this.expirationTimeInSeconds = expirationTimeInSeconds;
        }

    }

}
