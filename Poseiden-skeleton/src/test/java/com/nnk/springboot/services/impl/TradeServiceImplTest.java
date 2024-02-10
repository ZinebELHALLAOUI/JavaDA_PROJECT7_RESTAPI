package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TradeServiceImplTest {

    private final TradeRepository tradeRepository = Mockito.mock(TradeRepository.class);
    private final TradeService tradeService = new TradeServiceImpl(tradeRepository);

    @Test
    public void testFindAllTrades() {
        // Mocking the repository
        List<Trade> expectedTrades = new ArrayList<>();
        when(tradeRepository.findAll()).thenReturn(expectedTrades);

        // Calling service method
        List<Trade> result = tradeService.findAllTrades();

        // Verifying the result
        assertEquals(expectedTrades, result);
    }

    @Test
    public void testFindTradeById() {
        // Mocking the repository
        int tradeId = 1;
        Trade expectedTrade = new Trade();
        when(tradeRepository.findById(tradeId)).thenReturn(Optional.of(expectedTrade));

        // Calling service method
        Optional<Trade> result = tradeService.findTradeById(tradeId);

        // Verifying the result
        assertTrue(result.isPresent());
        assertEquals(expectedTrade, result.get());
    }

    @Test
    public void testCreateTrade() {
        // Mocking the repository
        Trade trade = new Trade();
        when(tradeRepository.save(trade)).thenReturn(trade);

        // Calling service method
        Trade result = tradeService.createTrade(trade);

        // Verifying the result
        assertEquals(trade, result);
    }

    @Test
    public void testUpdateTrade() {
        // Mocking the repository
        int tradeId = 1;
        Trade updatedTrade = new Trade();
        when(tradeRepository.findById(tradeId)).thenReturn(Optional.of(new Trade()));
        when(tradeRepository.saveAndFlush(updatedTrade)).thenReturn(updatedTrade);

        // Calling service method
        Trade result = tradeService.updateTrade(tradeId, updatedTrade);

        // Verifying the result
        assertEquals(updatedTrade, result);
    }

    @Test
    public void testDeleteTrade() {
        // Mocking the repository
        int tradeId = 1;
        when(tradeRepository.existsById(tradeId)).thenReturn(true);

        // Calling service method
        tradeService.deleteTrade(tradeId);

        // Verifying the delete operation
        Mockito.verify(tradeRepository).deleteById(tradeId);
    }
}

