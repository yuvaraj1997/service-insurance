package com.boltech.service_insurance.factory.quote;

import com.boltech.service_insurance.dto.QuoteRequest;
import com.boltech.service_insurance.dto.quote.HomeDetails;
import com.boltech.service_insurance.factory.quote.dto.QuoteBreakdownResponse;
import com.boltech.service_insurance.model.InsurancePolicy;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class HomeQuoteCalculator implements QuoteCalculator {

    @Override
    public QuoteBreakdownResponse calculateQuote(InsurancePolicy insurancePolicy, QuoteRequest quoteRequest) {
        log.info("Calculating home Quote policyId=[{}]", insurancePolicy.getId());

        HomeDetails home = quoteRequest.getHome();

        BigDecimal base = insurancePolicy.getPremiumPerMonth();

        QuoteBreakdownResponse response = new QuoteBreakdownResponse();
        response.addComponent("Base premium", base);

        //Each room RM100
        BigDecimal roomCost = BigDecimal.valueOf(100L).multiply(BigDecimal.valueOf(home.getNumberOfRooms()));
        response.addComponent(home.getNumberOfRooms() + " Rooms", roomCost);

        if (home.getType().equals("Apartment")) {
            // Add Rm20 for apartment
            response.addComponent("Apartment", new BigDecimal(20));
        } else if(home.getType().equals("Landed")) {
            // Add Rm50 for landed
            response.addComponent("Landed", new BigDecimal(50));
        }

        return response;
    }
}
