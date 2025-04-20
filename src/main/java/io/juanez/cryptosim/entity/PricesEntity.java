package io.juanez.cryptosim.entity;

import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "PRICES")
public class PricesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double btc_ask;

    @Column(nullable = false)
    private Double btc_bid;

    @Column(nullable = false)
    private Double eth_ask;

    @Column(nullable = false)
    private Double eth_bid;

    private Timestamp ts;

    public PricesEntity() {}

    public PricesEntity(Double btc_ask, Double btc_bid, Double eth_ask, Double eth_bid) {
        this.btc_ask = btc_ask;
        this.btc_bid = btc_bid;
        this.eth_ask = eth_ask;
        this.eth_bid = eth_bid;
        Date d = new Date();
        ts = new Timestamp(d.getTime());
    }

    public Integer getId() {
        return id;
    }

    public Double getBtcAsk() {
        return btc_ask;
    }

    public Double getBtcBid() {
        return btc_bid;
    }

    public Double getEthAsk() {
        return eth_ask;
    }

    public Double getEthBid() {
        return eth_bid;
    }

    public Timestamp getTs() {
        return ts;
    }
}
