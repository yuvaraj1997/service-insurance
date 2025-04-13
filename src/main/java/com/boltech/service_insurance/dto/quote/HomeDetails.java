package com.boltech.service_insurance.dto.quote;

import lombok.Data;

@Data
public class HomeDetails implements QuoteDetails {

    String type;

    int numberOfRooms;

}
