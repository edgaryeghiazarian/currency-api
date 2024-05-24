package com.currencyapi.demo.model;

import com.currencyapi.demo.entity.CurrencyEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CurrencyDTO {
    private long id;
    private CurrencyEnum name;
    private double buyRate;
    private double sellRate;
    private long marketId;
}
