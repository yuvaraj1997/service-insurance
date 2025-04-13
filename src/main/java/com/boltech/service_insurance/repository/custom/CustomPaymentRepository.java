package com.boltech.service_insurance.repository.custom;

import com.boltech.service_insurance.model.Payment;

import java.util.List;

public interface CustomPaymentRepository {

    Payment getNextPaymentDue(String userPolicyId);

    List<Payment> getAllPaymentsByUserPolicyIdSortedByScheduledDate(String userPolicyId);

}
