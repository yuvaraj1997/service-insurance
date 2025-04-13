package com.boltech.service_insurance.factory.quote;

import com.boltech.service_insurance.constant.InsurancePolicyConstants;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QuoteCalculatorFactory {

    private final Map<InsurancePolicyConstants.Type, QuoteCalculator> calculatorMap;

    public QuoteCalculatorFactory() {
        this.calculatorMap = new HashMap<>();
        this.calculatorMap.put(InsurancePolicyConstants.Type.AUTO, new AutoQuoteCalculator());
        this.calculatorMap.put(InsurancePolicyConstants.Type.LIFE, new LifeQuoteCalculator());
        this.calculatorMap.put(InsurancePolicyConstants.Type.HOME, new HomeQuoteCalculator());
    }

    public QuoteCalculator getQuoteCalculator(InsurancePolicyConstants.Type type) {
        if (!this.calculatorMap.containsKey(type)) {
            throw new RuntimeException("Invalid type to get quote calculate type=" + type.name());
        }

        return this.calculatorMap.get(type);
    }
}
