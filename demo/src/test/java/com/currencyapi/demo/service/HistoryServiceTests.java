package com.currencyapi.demo.service;

import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.entity.CurrencyEnum;
import com.currencyapi.demo.entity.History;
import com.currencyapi.demo.repository.HistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceTests {
    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryService historyService;

    @Test
    public void HistoryService_GetHistoryById_ReturnEntity(){
        History history = new History();
        history.setBuyRate(20.0);
        history.setSellRate(25.0);

        when(historyRepository.findById(1L)).thenReturn(Optional.ofNullable(history));

        History savedHistory = historyService.getHistoryById(1L);

        Assertions.assertNotNull(savedHistory);
        Assertions.assertEquals(history.getSellRate(), savedHistory.getSellRate());
    }

    @Test
    public void HistoryService_AddCurrencyToHistory_ReturnHistoryEntity() {
        History history = new History();
        history.setBuyRate(20.0);
        history.setSellRate(25.0);

        Currency currency = new Currency();
        currency.setSellRate(25.0);
        currency.setBuyRate(20.0);
        currency.setName(CurrencyEnum.USD);

        history.setCurrency(currency);

        when(historyRepository.save(any(History.class))).thenReturn(history);

        History savedHistory = historyService.addCurrencyToHistory(currency);
        Assertions.assertNotNull(savedHistory);
        Assertions.assertEquals(savedHistory.getCurrency().getName(), CurrencyEnum.USD);
    }
}
