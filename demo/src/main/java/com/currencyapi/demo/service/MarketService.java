package com.currencyapi.demo.service;

import com.currencyapi.demo.entity.CurrencyEnum;
import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.entity.Market;
import com.currencyapi.demo.model.ExchangeRequest;
import com.currencyapi.demo.model.MarketDTO;
import com.currencyapi.demo.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MarketService {
    private final MarketRepository marketRepository;

    @Autowired
    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    public Market getMarketById(long id) {
        Optional<Market> marketOptional = marketRepository.findById(id);
        if (marketOptional.isEmpty()) {
            throw new RuntimeException("Market not found");
        }
        return marketOptional.get();
    }

    @Transactional
    public Market addMarket(MarketDTO marketDTO) {
        Market market = new Market();
        market.setName(marketDTO.getName());
        Market newMarket = marketRepository.save(market);
        return newMarket;
    }

    public Currency getCurrentCurrency(long marketId, CurrencyEnum currencyName) {
        Market market = getMarketById(marketId);
        List<Currency> currencyList = market.getCurrencyList();

        return currencyList.stream().filter(c -> c.getName().equals(currencyName)).reduce((first, second) -> second).get();
    }

    public int exchangeToAMD(ExchangeRequest exchangeRequest) {
        CurrencyEnum name = exchangeRequest.getFrom();
        double amount = exchangeRequest.getAmount();

        Currency currentCurrency = getCurrentCurrency(exchangeRequest.getMarketId(), name);
        int finalAmount = (int) (amount*currentCurrency.getBuyRate());
        return (int) (Math.ceil(finalAmount/10.0)*10);
    }

    public double exchangeFromAMD(ExchangeRequest exchangeRequest) {
        CurrencyEnum name = exchangeRequest.getTo();
        double amount = exchangeRequest.getAmount();

        Currency currentCurrency = getCurrentCurrency(exchangeRequest.getMarketId(), name);

        double finalAmount = amount / currentCurrency.getSellRate();
        return Math.round(finalAmount * 100.0) / 100.0;
    }

    public double exchangeToAny(ExchangeRequest exchangeRequest) {
        double amountInAMD = exchangeToAMD(exchangeRequest);

        ExchangeRequest newRequest = new ExchangeRequest();
        newRequest.setMarketId(exchangeRequest.getMarketId());
        newRequest.setTo(exchangeRequest.getTo());
        newRequest.setAmount(amountInAMD);

        double finalAmount = exchangeFromAMD(newRequest);

        return finalAmount;

    }

    @Transactional
    public Market updateMarket(long marketId, String name, List<Currency> currencyList) {
        Market market = getMarketById(marketId);
        market.setName(name);
        market.setCurrencyList(currencyList);
        Market updatedMarket = marketRepository.save(market);
        return updatedMarket;
    }
}
