package com.currencyapi.demo.controller;

import com.currencyapi.demo.entity.Market;
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
        Market market = marketService.addMarket(marketDTO);

        return new ResponseEntity<>("Market created: " + market.getName() + ", id:" + market.getId(),
                HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getMarket(@PathVariable long id) {
        Market market = marketService.getMarketById(id);

        return new ResponseEntity<>(market.getCurrencyList().get(market.getCurrencyList().size() - 1).toString(),
                HttpStatus.OK);
    }
}
