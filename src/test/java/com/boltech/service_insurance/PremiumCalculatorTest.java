package com.boltech.service_insurance;

import com.boltech.service_insurance.dto.QuoteRequest;
import com.boltech.service_insurance.dto.quote.HomeDetails;
import com.boltech.service_insurance.dto.quote.LifeDetails;
import com.boltech.service_insurance.factory.quote.HomeQuoteCalculator;
import com.boltech.service_insurance.factory.quote.LifeQuoteCalculator;
import com.boltech.service_insurance.factory.quote.dto.QuoteBreakdownResponse;
import com.boltech.service_insurance.model.InsurancePolicy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PremiumCalculatorTest {

    @Test
    void testHomeQuoteCalculator_apartment() {
        HomeQuoteCalculator calculator = new HomeQuoteCalculator();

        HomeDetails home = new HomeDetails();
        home.setNumberOfRooms(3);
        home.setType("Apartment");

        QuoteRequest request = new QuoteRequest();
        request.setHome(home);

        InsurancePolicy policy = new InsurancePolicy();
        policy.setPremiumPerMonth(new BigDecimal("200"));

        QuoteBreakdownResponse response = calculator.calculateQuote(policy, request);

        BigDecimal expected = new BigDecimal("200").add(new BigDecimal("300")).add(new BigDecimal("20")); // Base + 3*100 + Apartment
        assertEquals(expected, response.getTotal());
    }

    @Test
    void testLifeQuoteCalculator_ageOver50_poorHealth() {
        LifeQuoteCalculator calculator = new LifeQuoteCalculator();

        LifeDetails life = new LifeDetails();
        life.setAge(55);
        life.setHealthStatus("Poor");

        QuoteRequest request = new QuoteRequest();
        request.setLife(life);


        InsurancePolicy policy = new InsurancePolicy();
        policy.setPremiumPerMonth(new BigDecimal("200"));

        QuoteBreakdownResponse response = calculator.calculateQuote(policy, request);

        assertEquals(new BigDecimal("400"), response.getTotal()); // 200 + 100 (age) + 100 (poor)
        assertEquals(3, response.getComponents().size());
    }

}
