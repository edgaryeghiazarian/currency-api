package com.currencyapi.demo.service;

import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.entity.CurrencyEnum;
import com.currencyapi.demo.entity.Market;
import com.currencyapi.demo.model.ExchangeRequest;
import com.currencyapi.demo.model.MarketDTO;
import com.currencyapi.demo.repository.MarketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MarketServiceTests {
    @Mock
    private MarketRepository marketRepository;
    @InjectMocks
    private MarketService marketService;

    @Test
    public void MarketService_CreateMarket() {
        Market market = new Market();
        market.setName("SAS");

        MarketDTO marketDTO = MarketDTO.builder().name("SAS").build();

        when(marketRepository.save(Mockito.any(Market.class))).thenReturn(market);

        MarketDTO saveMarked = marketService.addMarket(marketDTO);

        Assertions.assertNotNull(saveMarked);
    }

    @Test
    public void MarketService_GetMarketById() {
        Market market = new Market();
        market.setName("SAS");

        when(marketRepository.findById(1L)).thenReturn(Optional.ofNullable(market));

        Market saveMarked = marketService.getMarketById(1L);

        Assertions.assertNotNull(saveMarked);
        Assertions.assertEquals(saveMarked.getName(), market.getName());
    }

    @Test
    public void MarketService_GetCurrentCurrency() {
        long marketId = 1L;
        Market market = new Market();
        market.setName("SAS");
        CurrencyEnum currencyName = CurrencyEnum.USD;

        Currency currency1 = new Currency();
        currency1.setName(CurrencyEnum.USD);

        Currency currency2 = new Currency();
        currency2.setName(CurrencyEnum.EUR);

        List<Currency> currencyList = Arrays.asList(currency1, currency2);
        market = mock(Market.class);

        when(marketRepository.findById(marketId)).thenReturn(Optional.ofNullable(market));
        when(market.getCurrencyList()).thenReturn(currencyList);

        Currency result = marketService.getCurrentCurrency(marketId, currencyName);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(currencyName, result.getName());

    }

    @Test
    public void MarketService_ExchangeToAMD() {
        long marketId = 1L;
        CurrencyEnum currencyName = CurrencyEnum.USD;
        double amount = 20.0;
        double buyRate = 400.0;
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .amount(amount)
                .from(currencyName)
                .marketId(marketId).build();

        Currency currentCurrency = new Currency();
        currentCurrency.setName(currencyName);
        currentCurrency.setBuyRate(buyRate);

        Market market = new Market();
        market.setName("SAS");
        market = mock(Market.class);

        when(market.getCurrencyList()).thenReturn(List.of(currentCurrency));
        when(marketRepository.findById(marketId)).thenReturn(Optional.ofNullable(market));

        int result = marketService.exchangeToAMD(exchangeRequest);
        int expected = (int) Math.ceil(((amount * buyRate) / 10.0) * 10);

        Assertions.assertEquals(result, expected);

    }

    @Test
    public void MarketService_ExchangeFromAMD() {
        long marketId = 1L;
        CurrencyEnum currencyName = CurrencyEnum.USD;
        double amount = 20000.0;
        double sellRate = 420.0;
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .amount(amount)
                .to(currencyName)
                .marketId(marketId).build();

        Currency currentCurrency = new Currency();
        currentCurrency.setName(currencyName);
        currentCurrency.setSellRate(sellRate);

        Market market = new Market();
        market.setName("SAS");
        market = mock(Market.class);

        when(market.getCurrencyList()).thenReturn(List.of(currentCurrency));
        when(marketRepository.findById(marketId)).thenReturn(Optional.ofNullable(market));

        double result = marketService.exchangeFromAMD(exchangeRequest);

        double expected = (Math.round((amount / sellRate) * 100.0)) / 100.0;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void MarketService_ExchangeToAny() {
        long marketId = 1L;
        CurrencyEnum currencyName1 = CurrencyEnum.USD;
        CurrencyEnum currencyName2 = CurrencyEnum.GEL;
        double amount = 20.0;
        double buyRate = 400.0;
        double sellRate = 155.0;
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .amount(amount)
                .from(currencyName1)
                .to(currencyName2)
                .marketId(marketId).build();

        Currency currentCurrency = new Currency();
        currentCurrency.setName(currencyName1);
        currentCurrency.setBuyRate(buyRate);

        Currency currencyCurrency2 = new Currency();
        currencyCurrency2.setName(currencyName2);
        currencyCurrency2.setSellRate(sellRate);

        Market market = new Market();
        market.setName("SAS");
        market = mock(Market.class);

        when(market.getCurrencyList()).thenReturn(List.of(currentCurrency, currencyCurrency2));
        when(marketRepository.findById(marketId)).thenReturn(Optional.ofNullable(market));

        double result = marketService.exchangeToAny(exchangeRequest);
        int toAMD = (int) Math.ceil(((amount * buyRate) / 10.0) * 10);
        double expected = (Math.round((toAMD / sellRate) * 100.0)) / 100.0;

        Assertions.assertEquals(result, expected);
    }

}
