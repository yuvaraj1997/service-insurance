package com.boltech.service_insurance.repository.custom;

import com.boltech.service_insurance.constant.PaymentConstants;
import com.boltech.service_insurance.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomPaymentRepositoryImpl implements CustomPaymentRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Payment getNextPaymentDue(String userPolicyId) {
        Query query = new Query(Criteria.where("userPolicyId").is(userPolicyId).and("status").is(PaymentConstants.Status.SCHEDULED.name()))
                .with(Sort.by(Sort.Direction.ASC, "billingSchedule"))
                .limit(1);

        return mongoTemplate.findOne(query, Payment.class);
    }

    @Override
    public List<Payment> getAllPaymentsByUserPolicyIdSortedByScheduledDate(String userPolicyId) {
        Query query = new Query(Criteria.where("userPolicyId").is(userPolicyId))
                .with(Sort.by(Sort.Direction.ASC, "billingSchedule"));

        return mongoTemplate.find(query, Payment.class);
    }
}
