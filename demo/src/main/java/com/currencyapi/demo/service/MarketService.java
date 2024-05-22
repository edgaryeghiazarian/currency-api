package com.currencyapi.demo.service;

import com.currencyapi.demo.entity.Market;
import com.currencyapi.demo.model.MarketDTO;
import com.currencyapi.demo.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
