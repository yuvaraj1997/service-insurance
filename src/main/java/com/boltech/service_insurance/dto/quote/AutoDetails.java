package com.boltech.service_insurance.dto.quote;

import lombok.Data;

@Data
public class AutoDetails implements QuoteDetails {

    String type;

    boolean additionalDriver;

    boolean naturalDisaster;

}
