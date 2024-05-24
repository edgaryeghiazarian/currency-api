package com.currencyapi.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MarketDTO {
    private long id;
    private String name;
    private List<CurrencyDTO> currencyDTOList;
}
