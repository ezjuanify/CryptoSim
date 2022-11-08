package com.dotjuan89.aquariuxtest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.List;

public class HoubiEntity {
    public static class houbiItem {
        @JsonProperty
        private String symbol;

        @JsonProperty
        private double open;

        @JsonProperty
        private double high;

        @JsonProperty
        private double low;

        @JsonProperty
        private double close;

        @JsonProperty
        private double amount;

        @JsonProperty
        private double vol;

        @JsonProperty
        private long count;

        @JsonProperty
        private double bid;

        @JsonProperty
        private double bidSize;

        @JsonProperty
        private double ask;

        @JsonProperty
        private double askSize;

        public String getSymbol() {return symbol;}
        public double getOpen() {return open;}
        public double getHigh() {return high;}
        public double getLow() {return low;}
        public double getClose() {return close;}
        public double getAmount() {return amount;}
        public double getVol() {return vol;}
        public long getCount() {return count;}
        public double getBid() {return bid;}
        public double getBidSize() {return bidSize;}
        public double getAsk() {return ask;}
        public double getAskSize() {return askSize;}
    }

    @JsonProperty
    private List<houbiItem> data;

    @JsonProperty
    private String status;

    @JsonProperty
    private Timestamp ts;

    public houbiItem get(int i) {return data.get(i);}
    public List<houbiItem> getData() {return data;}
    public String getStatus() {return status;}
    public Timestamp getTs() {return ts;}
}
