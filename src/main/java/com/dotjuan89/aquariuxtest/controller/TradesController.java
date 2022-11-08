package com.dotjuan89.aquariuxtest.controller;

import com.dotjuan89.aquariuxtest.entity.PriceEntity;
import com.dotjuan89.aquariuxtest.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradesController {
    @Autowired
    PriceRepository priceRepository;

    @RequestMapping(value = "/latest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceEntity> getLatestPrice() {
        return new ResponseEntity<>(priceRepository.findTopByOrderByIdDesc(), HttpStatus.OK);
    }
}
