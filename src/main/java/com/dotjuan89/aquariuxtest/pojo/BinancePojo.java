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

    public String getBidPrice() {
        return bidPrice;
    }

    public String getBidQty() {
        return bidQty;
    }

    public String getAskPrice() {
        return askPrice;
    }

    public String getAskQty() {
        return askQty;
    }
}
