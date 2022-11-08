package com.dotjuan89.aquariuxtest.service;

import com.dotjuan89.aquariuxtest.entity.BinanceEntity;
import com.dotjuan89.aquariuxtest.entity.HoubiEntity;
import com.dotjuan89.aquariuxtest.entity.PricesEntity;
import com.dotjuan89.aquariuxtest.repository.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PricesRepository pricesRepository;

    private enum TradeKey {
        BTC_ASK,
        BTC_BID,
        ETH_ASK,
        ETH_BID
    }
    private static final String BINANCE = "https://api.binance.com/api/v3/ticker/bookTicker";
    private static final String HOUBI = "https://api.huobi.pro/market/tickers";
    private static final String BTCPAIR = "BTCUSDT";
    private static final String ETHPAIR = "ETHUSDT";
    private static RestTemplate restTemplate = null;

    private void getBinancePrice(Map<TradeKey, Double> m) {
        int tradeKeysFound = 0;

        BinanceEntity[] binanceResult = restTemplate.getForObject(BINANCE, BinanceEntity[].class);
        for (BinanceEntity i : binanceResult) {
            if (i.getSymbol().equals(BTCPAIR)) {
                // BTC BUY Lowest
                if (!m.containsKey(TradeKey.BTC_ASK) || i.getAskPrice() < m.get(TradeKey.BTC_ASK)) {
                    m.put(TradeKey.BTC_ASK, i.getAskPrice());
                }

                // BTC SELL Highest
                if (!m.containsKey(TradeKey.BTC_BID) || i.getBidPrice() > m.get(TradeKey.BTC_BID)) {
                    m.put(TradeKey.BTC_BID, i.getBidPrice());
                }
                tradeKeysFound += 2;
            }

            if (i.getSymbol().equals(ETHPAIR)){
                if (!m.containsKey(TradeKey.ETH_ASK) || i.getAskPrice() < m.get(TradeKey.ETH_ASK)) {
                    m.put(TradeKey.ETH_ASK, i.getAskPrice());
                }

                if (!m.containsKey(TradeKey.ETH_BID) || i.getBidPrice() > m.get(TradeKey.ETH_BID)) {
                    m.put(TradeKey.ETH_BID, i.getBidPrice());
                }
                tradeKeysFound += 2;
            }

            if (verbose && (i.getSymbol().equals(BTCPAIR) || i.getSymbol().equals(ETHPAIR))) {
                Date d = new Date();
                Timestamp ts = new Timestamp(d.getTime());
                System.out.printf("%s --- %s, %s, %s\n", ts, i.getSymbol(), i.getAskPrice(), i.getBidPrice());
            }
            if (tradeKeysFound == TradeKey.values().length) {
                break;
            }
        }
    }

    private void getHoubiPrice(Map<TradeKey, Double> m) {
        int tradeKeysFound = 0;
        HoubiEntity houbiResult = restTemplate.getForObject(HOUBI, HoubiEntity.class);
        for  (HoubiEntity.houbiItem i : houbiResult.getData()) {
            if (i.getSymbol().equals(BTCPAIR)) {
                // BTC BUY Lowest
                if (!m.containsKey(TradeKey.BTC_ASK) || i.getAsk() < m.get(TradeKey.BTC_ASK)) {
                    m.put(TradeKey.BTC_ASK, i.getAsk());
                }

                // BTC SELL Highest
                if (!m.containsKey(TradeKey.BTC_BID) || i.getBid() > m.get(TradeKey.BTC_BID)) {
                    m.put(TradeKey.BTC_BID, i.getBid());
                }
                tradeKeysFound += 2;
            }

            if (i.getSymbol().equals(ETHPAIR)) {
                // ETH BUY Lowest
                if (!m.containsKey(TradeKey.ETH_ASK) || i.getAsk() < m.get(TradeKey.ETH_ASK)) {
                    m.put(TradeKey.ETH_ASK, i.getAsk());
                }

                // ETH SELL Highest
                if (!m.containsKey(TradeKey.ETH_BID) || i.getBid() > m.get(TradeKey.ETH_BID)) {
                    m.put(TradeKey.ETH_BID, i.getBid());
                }
                tradeKeysFound += 2;
            }

            if (verbose && (i.getSymbol().equals(BTCPAIR) || i.getSymbol().equals(ETHPAIR))) {
                Date d = new Date();
                Timestamp ts = new Timestamp(d.getTime());
                System.out.printf("%s --- %s, %s, %s\n", ts, i.getSymbol(), i.getAsk(), i.getBid());
            }
            if (tradeKeysFound == TradeKey.values().length) {
                break;
            }
        }
    }

    private void StoreToH2(Map<TradeKey, Double> m) {
        PricesEntity entity = new PricesEntity(
                m.get(TradeKey.BTC_ASK),
                m.get(TradeKey.BTC_BID),
                m.get(TradeKey.ETH_ASK),
                m.get(TradeKey.ETH_BID)
        );

        pricesRepository.save(entity);
    }

    @Scheduled(fixedRate = 10000)
    public void getTrades() {
        restTemplate = new RestTemplate();

        Map<TradeKey, Double> m = new HashMap<>();
        getBinancePrice(m);
        getHoubiPrice(m);
        StoreToH2(m);
    }
}
