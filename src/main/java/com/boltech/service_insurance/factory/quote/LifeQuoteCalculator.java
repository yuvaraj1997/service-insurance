package com.boltech.service_insurance.factory.quote;

import com.boltech.service_insurance.dto.QuoteRequest;
import com.boltech.service_insurance.dto.quote.LifeDetails;
import com.boltech.service_insurance.factory.quote.dto.QuoteBreakdownResponse;
import com.boltech.service_insurance.model.InsurancePolicy;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class LifeQuoteCalculator implements QuoteCalculator {

    @Override
    public QuoteBreakdownResponse calculateQuote(InsurancePolicy insurancePolicy, QuoteRequest quoteRequest) {
        log.info("Calculating Life Quote policyId=[{}]", insurancePolicy.getId());

        LifeDetails life = quoteRequest.getLife();

        BigDecimal base = insurancePolicy.getPremiumPerMonth();

        QuoteBreakdownResponse response = new QuoteBreakdownResponse();
        response.addComponent("Base premium", base);

        if (life.getAge() > 50) {
            // Add RM100 for age above 50
            response.addComponent("Age more than 50", new BigDecimal(100));
        }

        if (life.getHealthStatus().equals("Poor")) {
            // Add RM100 for poor health (high risk)
            response.addComponent("Poor Health", new BigDecimal(100));
        } else if(life.getHealthStatus().equals("Good")) {
            // Add RM50 for Good health (moderate risk)
            response.addComponent("Good Health", new BigDecimal(50));
        }

        return response;
    }
}
