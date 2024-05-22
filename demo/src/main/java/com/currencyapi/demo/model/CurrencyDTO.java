package com.currencyapi.demo.model;

import com.currencyapi.demo.entity.Currencies;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDTO {
    private long id;
    private Currencies name;
    private double buyRate;
    private double sellRate;
    private long marketId;
}
