package com.dotjuan89.aquariuxtest.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BinancePojo {
    @JsonProperty
    private String symbol;
    @JsonProperty
    private String bidPrice;
    @JsonProperty
    private String bidQty;
    @JsonProperty
    private String askPrice;
    @JsonProperty
    private String askQty;

    public String getSymbol() {
        return symbol;
    }

    public double getBidPrice() {
        return Double.parseDouble(bidPrice);
    }

    public double getBidQty() {
        return Double.parseDouble(bidQty);
    }

    public double getAskPrice() {
        return Double.parseDouble(askPrice);
    }

    public double getAskQty() {
        return Double.parseDouble(askQty);
    }
}
