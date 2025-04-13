package com.boltech.service_insurance.repository;

import com.boltech.service_insurance.model.UserSession;
import com.boltech.service_insurance.repository.custom.CustomUserSessionRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface UserSessionRepository extends MongoRepository<UserSession, String>, CustomUserSessionRepository {

    UserSession getById(String id);

    @Query("{ '_id': ?0 }")
    @Update("{ '$set': { 'active': false } }")
    void deactivateById(String id);

}
