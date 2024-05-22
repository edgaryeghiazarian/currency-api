package com.currencyapi.demo.controller;

import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.model.CurrencyDTO;
import com.currencyapi.demo.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCurrency(@RequestBody CurrencyDTO currencyDTO) {
        Currency currency = currencyService.addCurrency(currencyDTO);

        return new ResponseEntity<>("Currency created: " + currency.getName() + " " + ", id: " + currency.getId(),
                HttpStatus.CREATED);
    }
}
