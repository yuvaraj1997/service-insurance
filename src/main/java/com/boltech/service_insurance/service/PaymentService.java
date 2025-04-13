package com.boltech.service_insurance.service;

import com.boltech.service_insurance.model.Payment;
import com.boltech.service_insurance.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment getNextPaymentDue(String userPolicyId) {
        return paymentRepository.getNextPaymentDue(userPolicyId);
    }

    public List<Payment> getAllPaymentsByUserPolicyIdSortedByScheduledDate(String userPolicyId) {
        return paymentRepository.getAllPaymentsByUserPolicyIdSortedByScheduledDate(userPolicyId);
    }
}
