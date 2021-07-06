package com.connect.sample01.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @JsonProperty("invoiceId")
    private String invoiceId;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("price")
    private double price;

    @JsonProperty("message")
    private String message;

}
