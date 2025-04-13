package com.boltech.service_insurance.security.dto;

import com.boltech.service_insurance.constant.JwtConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResult {

    private final String refreshToken;
    private final String accessToken;
    private final Long expiresInSeconds;
    private final Instant expiration;
    private final Instant issuedAt;
    @JsonIgnore
    private final String jti;

    public TokenResult(String token, String jti, Instant issuedAt, Instant expiration, JwtConstants.TokenType tokenType) {
        if (tokenType == JwtConstants.TokenType.REFRESH_TOKEN) {
            this.refreshToken = token;
            this.accessToken = null;
        } else {
            this.refreshToken = null;
            this.accessToken = token;
        }
        this.issuedAt = issuedAt;
        this.expiration = expiration;
        this.expiresInSeconds = ChronoUnit.SECONDS.between(issuedAt, expiration);
        this.jti = jti;
    }
}
