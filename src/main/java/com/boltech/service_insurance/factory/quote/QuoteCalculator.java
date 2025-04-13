package com.boltech.service_insurance.factory.quote;

import com.boltech.service_insurance.dto.QuoteRequest;
import com.boltech.service_insurance.factory.quote.dto.QuoteBreakdownResponse;
import com.boltech.service_insurance.model.InsurancePolicy;

public interface QuoteCalculator {

    QuoteBreakdownResponse calculateQuote(InsurancePolicy insurancePolicy, QuoteRequest quoteRequest);

}
