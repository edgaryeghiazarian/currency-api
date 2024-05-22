package com.currencyapi.demo.service;

import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.entity.History;
import com.currencyapi.demo.entity.Market;
import com.currencyapi.demo.model.CurrencyDTO;
import com.currencyapi.demo.repository.CurrencyRepository;
import com.currencyapi.demo.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final HistoryRepository historyRepository;
    private final MarketService marketService;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository, HistoryRepository historyRepository, MarketService marketService) {
        this.currencyRepository = currencyRepository;
        this.historyRepository = historyRepository;
        this.marketService = marketService;
    }

    @Transactional
    public Currency addCurrency(CurrencyDTO currencyDTO) {
        Currency currency = new Currency();
        currency.setName(currencyDTO.getName());
        currency.setBuyRate(currencyDTO.getBuyRate());
        currency.setSellRate(currencyDTO.getSellRate());
        Market market = marketService.getMarketById(currencyDTO.getMarketId());
        currency.setMarket(market);
        Currency newCurrency = currencyRepository.save(currency);

        History history = new History();
        history.setCurrency(newCurrency);
        history.setDate(new Date());
        historyRepository.save(history);

        return newCurrency;
    }

    public Currency getCurrency(long id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isEmpty()) {
            throw new RuntimeException("Currency not found");
        }
        return optionalCurrency.get();
    }
}
