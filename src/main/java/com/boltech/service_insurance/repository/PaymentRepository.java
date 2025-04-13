package com.boltech.service_insurance.repository;

import com.boltech.service_insurance.model.Payment;
import com.boltech.service_insurance.repository.custom.CustomPaymentRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String>, CustomPaymentRepository {

}
