package com.connect.sample01.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;
@Data
public class Order {

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("items")
    private List<LineItem> items;

    @JsonProperty("shippingAddress")
    private String shippingAddress;

}
