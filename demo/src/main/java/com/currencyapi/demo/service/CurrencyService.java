package com.currencyapi.demo.service;

import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.entity.Market;
import com.currencyapi.demo.model.CurrencyDTO;
import com.currencyapi.demo.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final HistoryService historyService;
    private final MarketService marketService;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository, HistoryService historyService, MarketService marketService) {
        this.currencyRepository = currencyRepository;
        this.historyService = historyService;
        this.marketService = marketService;
    }

    @Transactional
    public CurrencyDTO addCurrency(CurrencyDTO currencyDTO) {
        Currency currency = new Currency();
        currency.setName(currencyDTO.getName());
        currency.setBuyRate(currencyDTO.getBuyRate());
        currency.setSellRate(currencyDTO.getSellRate());
        Market market = marketService.getMarketById(currencyDTO.getMarketId());
        currency.setMarket(market);
        Currency newCurrency = currencyRepository.save(currency);

        return CurrencyDTO.builder()
                .marketId(market.getId())
                .name(newCurrency.getName())
                .sellRate(newCurrency.getSellRate())
                .buyRate(newCurrency.getBuyRate())
                .id(newCurrency.getId())
                .build();
    }

    public Currency getCurrencyById(long id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isEmpty()) {
            throw new RuntimeException("Currency not found");
        }
        return optionalCurrency.get();
    }

    public CurrencyDTO getCurrency(long id) {
        Currency currency = getCurrencyById(id);

        return CurrencyDTO.builder()
                .id(currency.getId())
                .buyRate(currency.getBuyRate())
                .sellRate(currency.getSellRate())
                .name(currency.getName())
                .marketId(currency.getMarket().getId())
                .build();
    }

    @Transactional
    public CurrencyDTO updateCurrency(CurrencyDTO currencyDTO) {
        Market market = marketService.getMarketById(currencyDTO.getMarketId());
        Currency currency = market.getCurrencyList().stream().filter(c -> c.getName().equals(currencyDTO.getName())).findFirst().get();
        historyService.addCurrencyToHistory(currency);

        currency.setBuyRate(currencyDTO.getBuyRate());
        currency.setSellRate(currencyDTO.getSellRate());
        Currency updatedCurrency = currencyRepository.save(currency);

        return CurrencyDTO.builder()
                .marketId(updatedCurrency.getId())
                .name(updatedCurrency.getName())
                .sellRate(updatedCurrency.getSellRate())
                .buyRate(updatedCurrency.getBuyRate())
                .id(updatedCurrency.getId())
                .build();
    }
}
