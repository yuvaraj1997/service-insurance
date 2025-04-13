package com.boltech.service_insurance.repository.custom;

public interface CustomUserSessionRepository {

    void deactivateSessionsByUserId(String userId);
}
