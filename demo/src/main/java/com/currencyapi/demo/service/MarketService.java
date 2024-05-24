package com.currencyapi.demo.service;

import com.currencyapi.demo.entity.CurrencyEnum;
import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.entity.Market;
import com.currencyapi.demo.model.CurrencyDTO;
import com.currencyapi.demo.model.ExchangeRequest;
import com.currencyapi.demo.model.MarketDTO;
import com.currencyapi.demo.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public MarketDTO getMarket(long id) {
        Market market = getMarketById(id);

        List<CurrencyDTO> currencyDTOList = new ArrayList<>();
        for (Currency currency : market.getCurrencyList()) {
            CurrencyDTO currencyDTO = CurrencyDTO.builder()
                    .sellRate(currency.getSellRate())
                    .buyRate(currency.getBuyRate())
                    .id(currency.getId())
                    .name(currency.getName())
                    .marketId(market.getId())
                    .build();
            currencyDTOList.add(currencyDTO);
        }
        return MarketDTO.builder().id(id).name(market.getName()).currencyDTOList(currencyDTOList).build();
    }

    @Transactional
    public MarketDTO addMarket(MarketDTO marketDTO) {
        Market market = new Market();
        market.setName(marketDTO.getName());
        Market newMarket = marketRepository.save(market);
        return MarketDTO.builder().id(newMarket.getId()).name(newMarket.getName()).build();
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
    public MarketDTO updateMarket(MarketDTO marketDTO) {
        //todo not tested
        Market market = getMarketById(marketDTO.getId());
        market.setName(market.getName());
        marketRepository.save(market);
        return MarketDTO.builder().id(market.getId()).name(market.getName()).build();
    }
}
