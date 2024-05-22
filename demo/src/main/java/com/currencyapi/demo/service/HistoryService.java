package com.currencyapi.demo.service;

import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.entity.History;
import com.currencyapi.demo.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public History addCurrencyToHistory(Currency currency) {
        History history = new History();
        history.setCurrency(currency);
        history.setDate(new Date());
        History newHistory = historyRepository.save(history);

        return newHistory;
    }
}