package com.boltech.service_insurance.repository.custom;

import com.boltech.service_insurance.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class CustomUserSessionRepositoryImpl implements CustomUserSessionRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void deactivateSessionsByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update().set("isActive", false);
        mongoTemplate.updateMulti(query, update, UserSession.class);
    }
}
