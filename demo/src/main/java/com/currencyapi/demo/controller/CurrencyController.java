package com.currencyapi.demo.controller;

import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.model.CurrencyDTO;
import com.currencyapi.demo.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        CurrencyDTO currency = currencyService.addCurrency(currencyDTO);

        return new ResponseEntity<>(currency, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCurrency(@PathVariable long id) {
        CurrencyDTO currency = currencyService.getCurrency(id);

        return new ResponseEntity<>(currency, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllCurrencies() {
        List<CurrencyDTO> allCurrencies = currencyService.getAllCurrencies();

        return new ResponseEntity<>(allCurrencies, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCurrency(@RequestBody CurrencyDTO currencyDTO) {
        CurrencyDTO currency = currencyService.updateCurrency(currencyDTO);

        return new ResponseEntity<>(currency, HttpStatus.OK);
    }
}
