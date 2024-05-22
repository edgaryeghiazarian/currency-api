package com.currencyapi.demo.model;

import com.currencyapi.demo.entity.CurrencyEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRequest {
    private CurrencyEnum name;
    private double amount;
    private long marketId;
}
