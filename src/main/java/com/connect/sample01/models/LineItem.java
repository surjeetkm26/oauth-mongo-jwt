package com.connect.sample01.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class LineItem {

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("quantity")
    private int quantity;

}
