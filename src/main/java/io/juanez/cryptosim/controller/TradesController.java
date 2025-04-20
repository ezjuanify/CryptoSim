package io.juanez.cryptosim.controller;

import io.juanez.cryptosim.entity.*;
import io.juanez.cryptosim.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.*;

@RestController
public class TradesController {
    @Autowired
    PricesRepository pricesRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    private static final String C_BTC = "btc";
    private static final String C_ETH = "eth";
    private static final String T_BUY = "buy";
    private static final String T_SELL = "sell";
    private static final String TS_BUY = "b";
    private static final String TS_SELL = "s";


    @RequestMapping(value = "/latest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PricesEntity> getLatestPrice() {
        return new ResponseEntity<>(pricesRepository.findTopByOrderByIdDesc(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/{currency}/{opt}/{amt}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity buyBtc(@PathVariable("id") int id, @PathVariable("currency") String currency, @PathVariable("opt") String opt, @PathVariable("amt") Double amt) {
        if (!currency.equals(C_BTC) && !currency.equals(C_ETH)) {
            return new ResponseEntity("{\"message\": \"Invalid trade type (btc / eth)\"}", HttpStatus.BAD_REQUEST);
        }

        if (!opt.equals(T_BUY) && !opt.equals(T_SELL) && !opt.equals(TS_BUY) && !opt.equals(TS_SELL)) {
            return new ResponseEntity("{\"message\": \"Invalid transaction option (buy / sell)\"}", HttpStatus.BAD_REQUEST);
        }

        Optional<UsersEntity> result = usersRepository.findById(id);
        if (result.isEmpty()) {
            return new ResponseEntity("{\"message\": \"ID not found\"}", HttpStatus.NOT_FOUND);
        }
        UsersEntity u = result.get();
        PricesEntity p = pricesRepository.findTopByOrderByIdDesc();

        // Amt is USDT
        if (opt.equals(T_BUY) || opt.equals(TS_BUY)) {
            Double r = u.getUsdt() - amt;
            if (r < 0) {
                return new ResponseEntity("{\"message\": \"Below USDT balance amount\"}", HttpStatus.BAD_REQUEST);
            }
            u.setUsdt(r);
            if (currency.equals(C_BTC)) {
                u.setBtc(u.getBtc() + (amt / p.getBtcAsk()));
            }
            if (currency.equals(C_ETH)){
                u.setEth(u.getEth() + (amt / p.getEthAsk()));
            }
        }

        // Amt is Crypto
        if (opt.equals(T_SELL) || opt.equals(TS_SELL)) {
            Double r = 0.0;
            Double resultCalc = 0.0;
            if (currency.equals(C_BTC)) {
                resultCalc = u.getBtc() - amt;
                if (resultCalc < 0) {
                    return new ResponseEntity("{\"message\": \"Below BTC balance amount\"}", HttpStatus.BAD_REQUEST);
                }
                u.setBtc(resultCalc);
                r = amt * p.getBtcBid();
            }
            if (currency.equals(C_ETH)) {
                resultCalc = u.getEth() - amt;
                if (resultCalc < 0) {
                    return new ResponseEntity("{\"message\": \"Below ETH balance amount\"}", HttpStatus.BAD_REQUEST);
                }
                u.setEth(resultCalc);
                r = amt * p.getEthAsk();
            }
            u.setUsdt(u.getUsdt() + r);
        }

        usersRepository.save(u);
        transactionsRepository.save(new TransactionsEntity(u, currency, opt, amt));
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/balance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBalance(@PathVariable("id") int id) {
        Optional<UsersEntity> result = usersRepository.findById(id);
        if (result.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        UsersEntity u = result.get();

        return new ResponseEntity(u, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/transactions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTransactions(@PathVariable("id") int id) {
        List<TransactionsEntity> transactionList = transactionsRepository.findTransactionsByUsersId(id);
        if (transactionList.size() == 0) {
            return new ResponseEntity("{\"message\": \"Transactions not found\"}" , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(transactionList, HttpStatus.OK);
    }
}
