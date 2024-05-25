package com.currencyapi.demo.service;

import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.entity.History;
import com.currencyapi.demo.model.HistoryDTO;
import com.currencyapi.demo.repository.CurrencyRepository;
import com.currencyapi.demo.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
        history.setBuyRate(currency.getBuyRate());
        history.setSellRate(currency.getSellRate());
        history.setTimestamp(LocalDateTime.now());
        History newHistory = historyRepository.save(history);

        return newHistory;
    }

    public History getHistoryById(long id) {
        Optional<History> optionalHistory = historyRepository.findById(id);
        if (optionalHistory.isEmpty()) {
            throw new RuntimeException("History not found");
        }
        return optionalHistory.get();
    }


    public HistoryDTO getHistory(long id) {
        History history = getHistoryById(id);
        return HistoryDTO.builder()
                .buyRate(history.getBuyRate())
                .sellRate(history.getSellRate())
                .currencyEnum(history.getCurrency().getName())
                .marketId(history.getCurrency().getMarket().getId())
                .id(history.getId())
                .timestamp(history.getTimestamp()).build();
    }

}