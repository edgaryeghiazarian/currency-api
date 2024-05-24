package com.currencyapi.demo.controller;

import com.currencyapi.demo.entity.Market;
import com.currencyapi.demo.model.ExchangeRequest;
import com.currencyapi.demo.model.MarketDTO;
import com.currencyapi.demo.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market")
public class MarketController {
    private final MarketService marketService;

    @Autowired
    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMarket(@RequestBody MarketDTO marketDTO) {
        MarketDTO market = marketService.addMarket(marketDTO);

        return new ResponseEntity<>(market, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getMarket(@PathVariable long id) {
        MarketDTO market = marketService.getMarket(id);

        return new ResponseEntity<>(market, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMarket(@RequestBody MarketDTO marketDTO) {
        MarketDTO market = marketService.updateMarket(marketDTO);
        return new ResponseEntity<>(market, HttpStatus.OK);
    }
    @PostMapping("/exchangeToAMD")
    public ResponseEntity<?> exchangeToAMD(@RequestBody ExchangeRequest exchangeRequest) {
        int exchangeToAMD = marketService.exchangeToAMD(exchangeRequest);

        return new ResponseEntity<>(exchangeToAMD, HttpStatus.OK);
    }

    @PostMapping("/exchangeFromAMD")
    public ResponseEntity<?> exchangeFromAMD(@RequestBody ExchangeRequest exchangeRequest) {
        double exchange = marketService.exchangeFromAMD(exchangeRequest);
        return new ResponseEntity<>(exchange, HttpStatus.OK);
    }

    @PostMapping("/exchange")
    public ResponseEntity<?> exchangeToAny(@RequestBody ExchangeRequest exchangeRequest) {
        double exchange = marketService.exchangeToAny(exchangeRequest);

        return new ResponseEntity<>(exchange, HttpStatus.OK);
    }
}
