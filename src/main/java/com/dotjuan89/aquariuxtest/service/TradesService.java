package com.dotjuan89.aquariuxtest.service;

import com.dotjuan89.aquariuxtest.pojo.BinancePojo;
import com.dotjuan89.aquariuxtest.pojo.HoubiPojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TradesService {
    @Value("${trade.verbose}")
    private boolean verbose;

    private enum TradeKey {
        BTC_ASK,
        BTC_BID,
        ETH_ASK,
        ETH_BID
    }

    private static final String BTCPAIR = "BTCUSDT";
    private static final String ETHPAIR = "ETHUSDT";
    private static final String BINANCE = "https://api.binance.com/api/v3/ticker/bookTicker";
    private static final String HOUBI = "https://api.huobi.pro/market/tickers";

    @Scheduled(fixedRate = 10000)
    public void getTrades() {
        RestTemplate restTemplate = new RestTemplate();
        BinancePojo[] binanceResult = restTemplate.getForObject(BINANCE, BinancePojo[].class);
        HoubiPojo houbiResult = restTemplate.getForObject(HOUBI, HoubiPojo.class);

        Date d = new Date();
        Timestamp ts = new Timestamp(d.getTime());

        Map<TradeKey, Double> m = new HashMap<>();
        for (BinancePojo i : binanceResult) {
            if (i.getSymbol().equals(BTCPAIR)) {
                m.put(TradeKey.BTC_ASK, i.getAskPrice());
                m.put(TradeKey.BTC_BID, i.getBidPrice());
            }

            if (i.getSymbol().equals(ETHPAIR)){
                m.put(TradeKey.ETH_ASK, i.getAskPrice());
                m.put(TradeKey.ETH_BID, i.getBidPrice());
            }

            if (verbose && (i.getSymbol().equals(BTCPAIR) || i.getSymbol().equals(ETHPAIR))) {
                System.out.printf("%s --- %s, %s, %s\n", ts, i.getSymbol(), i.getAskPrice(), i.getBidPrice());
            }
            if (m.size() == TradeKey.values().length) {
                break;
            }
        }

        int tradeKeysFound = 0;
        for  (HoubiPojo.houbiItem i : houbiResult.getData()) {
            if (i.getSymbol().equals(BTCPAIR)) {
                // BTC BUY Lowest
                if (i.getAsk() < m.get(TradeKey.BTC_ASK)) {
                    m.put(TradeKey.BTC_ASK, i.getAsk());
                }

                // BTC SELL Highest
                if (i.getBid() > m.get(TradeKey.BTC_BID)) {
                    m.put(TradeKey.BTC_BID, i.getBid());
                }
                tradeKeysFound += 2;
            }

            if (i.getSymbol().equals(ETHPAIR)) {
                // ETH BUY Lowest
                if (i.getAsk() < m.get(TradeKey.ETH_ASK)) {
                    m.put(TradeKey.ETH_ASK, i.getAsk());
                }

                // ETH SELL Highest
                if (i.getBid() > m.get(TradeKey.ETH_BID)) {
                    m.put(TradeKey.ETH_BID, i.getBid());
                }
                tradeKeysFound += 2;
            }

            if (verbose && (i.getSymbol().equals(BTCPAIR) || i.getSymbol().equals(ETHPAIR))) {
                System.out.printf("%s --- %s, %s, %s\n", ts, i.getSymbol(), i.getAsk(), i.getBid());
            }
            if (tradeKeysFound == TradeKey.values().length) {
                break;
            }
        }
    }
}
