package com.dotjuan89.aquariuxtest.controller;

import com.dotjuan89.aquariuxtest.entity.*;
import com.dotjuan89.aquariuxtest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TradesController {
    @Autowired
    PricesRepository pricesRepository;

    @Autowired
    UsersRepository usersRepository;

    private static final String C_BTC = "btc";
    private static final String C_ETH = "eth";
    private static final String T_BUY = "buy";
    private static final String T_SELL = "sell";

    @RequestMapping(value = "/latest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PricesEntity> getLatestPrice() {
        return new ResponseEntity<>(pricesRepository.findTopByOrderByIdDesc(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/{type}/{opt}/{amt}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity buyBtc(@PathVariable("id") int id, @PathVariable("type") String type, @PathVariable("opt") String opt, @PathVariable("amt") Double amt) {
        if (!type.equals(C_BTC) && !type.equals(C_ETH)) {
            return new ResponseEntity("Invalid trade type (btc / eth)", HttpStatus.BAD_REQUEST);
        }

        if (!opt.equals(T_BUY) && !opt.equals(T_SELL)) {
            return new ResponseEntity("Invalid transaction option (buy / sell)", HttpStatus.BAD_REQUEST);
        }

        Optional<UsersEntity> result = usersRepository.findById(id);
        if (result.isEmpty()) {
            return new ResponseEntity("ID not found", HttpStatus.NOT_FOUND);
        }
        UsersEntity u = result.get();
        PricesEntity p = pricesRepository.findTopByOrderByIdDesc();

        // Amt is USDT
        if (opt.equals(T_BUY)) {
            Double r = u.getUsdt() - amt;
            if (r < 0) {
                return new ResponseEntity("Below USDT balance amount", HttpStatus.BAD_REQUEST);
            }
            u.setUsdt(r);
            if (type.equals(C_BTC)) {
                u.setBtc(u.getBtc() + (amt / p.getBtcAsk()));
            }
            if (type.equals(C_ETH)){
                u.setEth(u.getEth() + (amt / p.getEthAsk()));
            }
        }

        // Amt is Crypto
        if (opt.equals(T_SELL)) {
            Double r = 0.0;
            Double resultCalc = 0.0;
            if (type.equals(C_BTC)) {
                resultCalc = u.getBtc() - amt;
                if (resultCalc < 0) {
                    return new ResponseEntity("Below BTC balance amount", HttpStatus.BAD_REQUEST);
                }
                u.setBtc(resultCalc);
                r = amt * p.getBtcBid();
            }
            if (type.equals(C_ETH)) {
                resultCalc = u.getEth() - amt;
                if (resultCalc < 0) {
                    return new ResponseEntity("Below ETH balance amount", HttpStatus.BAD_REQUEST);
                }

                r = amt * p.getEthAsk();
            }
            u.setUsdt(u.getUsdt() + r);
        }

        usersRepository.save(u);

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
}
