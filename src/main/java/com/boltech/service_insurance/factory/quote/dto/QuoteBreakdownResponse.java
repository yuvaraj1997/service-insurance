package com.boltech.service_insurance.factory.quote.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Data
public class QuoteBreakdownResponse {

    private BigDecimal total;
    private List<QuoteComponent> components = new LinkedList<>();

    public void addComponent(String description, BigDecimal amount) {
        components.add(new QuoteComponent(description, amount));
        total = total == null ? amount : total.add(amount);
    }

    @Data
    @AllArgsConstructor
    public static class QuoteComponent {
        private String description;
        private BigDecimal amount;
    }

}
