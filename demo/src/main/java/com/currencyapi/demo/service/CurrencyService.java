package com.currencyapi.demo.service;

import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.entity.Market;
import com.currencyapi.demo.model.CurrencyDTO;
import com.currencyapi.demo.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final MarketService marketService;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository, MarketService marketService) {
        this.currencyRepository = currencyRepository;
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
        return newCurrency;
    }
}
