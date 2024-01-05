package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Override
    public List<Trade> findAllTrades() {
        return tradeRepository.findAll();
    }

    @Override
    public Optional<Trade> findTradeById(Integer tradeId) {
        return tradeRepository.findById(tradeId);
    }

    @Override
    public Trade saveTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Override
    public void deleteTrade(Integer tradeId) {
        tradeRepository.deleteById(tradeId);
    }
}
