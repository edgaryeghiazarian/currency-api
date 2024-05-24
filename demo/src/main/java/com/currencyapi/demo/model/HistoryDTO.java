package com.currencyapi.demo.model;

import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.entity.CurrencyEnum;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class HistoryDTO {
    private long id;
    private CurrencyEnum currencyEnum;
    private long marketId;
    private double buyRate;
    private double sellRate;
    private LocalDateTime timestamp;
}
