package com.currencyapi.demo.service;

import com.currencyapi.demo.entity.Currency;
import com.currencyapi.demo.entity.CurrencyEnum;
import com.currencyapi.demo.entity.History;
import com.currencyapi.demo.entity.Market;
import com.currencyapi.demo.model.CurrencyDTO;
import com.currencyapi.demo.repository.CurrencyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTests {
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private HistoryService historyService;
    @Mock
    private MarketService marketService;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    public void CurrencyService_GetCurrencyById_ReturnEntity() {
        long currencyId = 1L;
        Currency currency = new Currency();
        currency.setName(CurrencyEnum.USD);
        currency.setBuyRate(200.0);
        currency.setBuyRate(250.0);

        when(currencyRepository.findById(currencyId)).thenReturn(Optional.ofNullable(currency));

        Currency currencyById = currencyService.getCurrencyById(currencyId);

        Assertions.assertNotNull(currencyById);
        Assertions.assertEquals(currencyById.getName(), currency.getName());
    }

    @Test
    public void CurrencyService_AddCurrency_ReturnCurrencyDTO() {
        Currency currency = new Currency();
        currency.setName(CurrencyEnum.USD);
        currency.setBuyRate(200.0);
        currency.setBuyRate(250.0);
        Market market = new Market();
        market.setId(1L);
        currency.setMarket(market);

        CurrencyDTO currencyDTO = CurrencyDTO.builder()
                .name(CurrencyEnum.USD)
                .buyRate(200.0)
                .sellRate(250.0)
                .marketId(1L).build();

        when(currencyRepository.save(Mockito.any(Currency.class))).thenReturn(currency);
        when(marketService.getMarketById(1L)).thenReturn(market);

        CurrencyDTO savedCurrency = currencyService.addCurrency(currencyDTO);

        Assertions.assertNotNull(savedCurrency);
        Assertions.assertEquals(savedCurrency.getBuyRate(), currency.getBuyRate());
    }

    @Test
    public void CurrencyService_UpdateCurrency_ReturnCurrencyDTO() {
        Currency currency = new Currency();
        currency.setName(CurrencyEnum.USD);
        currency.setBuyRate(200.0);
        currency.setBuyRate(250.0);
        Market market = new Market();
        market.setId(1L);
        List<Currency> currencyList = List.of(currency);
        currency.setMarket(market);

        History history = new History();
        history.setBuyRate(20.0);
        history.setSellRate(25.0);
        history.setCurrency(currency);
        when(historyService.addCurrencyToHistory(currency)).thenReturn(history);

        CurrencyDTO currencyDTO = CurrencyDTO.builder()
                .name(CurrencyEnum.USD)
                .buyRate(300.0)
                .sellRate(350.0)
                .marketId(1L).build();

        market = mock(Market.class);
        when(currencyRepository.save(Mockito.any(Currency.class))).thenReturn(currency);
        when(marketService.getMarketById(1L)).thenReturn(market);
        when(market.getCurrencyList()).thenReturn(currencyList);


        CurrencyDTO updatedCurrency = currencyService.updateCurrency(currencyDTO);

        Assertions.assertNotNull(updatedCurrency);
        Assertions.assertEquals(updatedCurrency.getBuyRate(), 300.0);
        Assertions.assertEquals(updatedCurrency.getSellRate(), 350.0);
    }

    @Test
    public void CurrencyService_GetAllCurrencies_ReturnList() {
        Currency currency1 = new Currency();
        currency1.setName(CurrencyEnum.USD);
        currency1.setId(1L);
        Currency currency2 = new Currency();
        currency2.setId(2L);
        currency2.setName(CurrencyEnum.EUR);

        Market market = new Market();
        market.setId(4L);
        currency1.setMarket(market);
        currency2.setMarket(market);

        when(currencyRepository.findById(1L)).thenReturn(Optional.ofNullable(currency1));
        when(currencyRepository.findById(2L)).thenReturn(Optional.ofNullable(currency2));

        when(currencyRepository.findAll()).thenReturn(List.of(currency1, currency2));

        List<CurrencyDTO> allCurrencies = currencyService.getAllCurrencies();

        Assertions.assertNotNull(allCurrencies);
        Assertions.assertEquals(2, allCurrencies.size());
        Assertions.assertEquals(allCurrencies.get(0).getName(), currency1.getName());
    }
}
