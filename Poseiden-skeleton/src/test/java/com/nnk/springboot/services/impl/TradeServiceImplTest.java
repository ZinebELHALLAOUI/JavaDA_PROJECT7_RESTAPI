package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TradeServiceImplTest {

    private final TradeRepository tradeRepository = mock(TradeRepository.class);
    private final TradeServiceImpl tradeService = new TradeServiceImpl(tradeRepository);

    @Test
    void testFindAllTrades() {
        Trade sampleTrade = new Trade();
        sampleTrade.setTradeId(1);
        sampleTrade.setAccount("SampleAccount");

        List<Trade> trades = new ArrayList<>();
        trades.add(sampleTrade);

        when(tradeRepository.findAll()).thenReturn(trades);

        List<Trade> result = tradeService.findAllTrades();

        assertEquals(1, result.size());
        assertEquals(sampleTrade, result.get(0));

        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    void testFindTradeById() {
        Trade sampleTrade = new Trade();
        sampleTrade.setTradeId(1);
        sampleTrade.setAccount("SampleAccount");

        when(tradeRepository.findById(1)).thenReturn(Optional.of(sampleTrade));

        Optional<Trade> result = tradeService.findTradeById(1);

        assertEquals(Optional.of(sampleTrade), result);

        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    void testSaveTrade() {
        Trade sampleTrade = new Trade();
        sampleTrade.setTradeId(1);
        sampleTrade.setAccount("SampleAccount");

        when(tradeRepository.save(sampleTrade)).thenReturn(sampleTrade);

        Trade result = tradeService.createTrade(sampleTrade);

        assertEquals(sampleTrade, result);

        verify(tradeRepository, times(1)).save(sampleTrade);
    }

    @Test
    void testDeleteTrade() {
        tradeService.deleteTrade(1);

        verify(tradeRepository, times(1)).deleteById(1);
    }
}