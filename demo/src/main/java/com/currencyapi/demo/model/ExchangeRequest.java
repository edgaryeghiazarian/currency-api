package com.currencyapi.demo.model;

import com.currencyapi.demo.entity.CurrencyEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRequest {
    private CurrencyEnum from;
    private CurrencyEnum to;
    private double amount;
    private long marketId;
}
