package com.boltech.service_insurance.factory.quote;

import com.boltech.service_insurance.dto.QuoteRequest;
import com.boltech.service_insurance.dto.quote.AutoDetails;
import com.boltech.service_insurance.factory.quote.dto.QuoteBreakdownResponse;
import com.boltech.service_insurance.model.InsurancePolicy;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class AutoQuoteCalculator implements QuoteCalculator {

    @Override
    public QuoteBreakdownResponse calculateQuote(InsurancePolicy insurancePolicy, QuoteRequest quoteRequest) {
        log.info("Calculating Auto Quote policyId=[{}]", insurancePolicy.getId());

        AutoDetails auto = quoteRequest.getAuto();

        BigDecimal base = insurancePolicy.getPremiumPerMonth();

        QuoteBreakdownResponse response = new QuoteBreakdownResponse();
        response.addComponent("Base premium", base);

        if (auto.getType().equals("car")) {
            // Add RM50 for standard car type
            response.addComponent("Standard Car", new BigDecimal(50));
        }

        if (auto.isAdditionalDriver()) {
            // Add RM30 if additional driver is covered
            response.addComponent("Additional Driver", new BigDecimal(30));
        }

        if (auto.isNaturalDisaster()) {
            // Add RM30 for natural disaster coverage
            response.addComponent("Natural Disaster", new BigDecimal(30));
        }

        return response;
    }
}
