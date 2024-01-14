package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.exceptions.Assert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Override
    public List<Trade> findAllTrades() {
        log.info("Find all trades");
        return tradeRepository.findAll();
    }

    @Override
    public Optional<Trade> findTradeById(final Integer tradeId) {
        log.info("Find trade by id : " + tradeId);
        Assert.notNull(tradeId, "trade id should not be null");
        return tradeRepository.findById(tradeId);
    }

    @Override
    public Trade createTrade(final Trade trade) {
        log.info("Creating trade : " + trade);
        Assert.isNull(trade.getTradeId(), "trade id should be null for creation");
        return tradeRepository.save(trade);
    }

    @Override
    public Trade updateTrade(final Trade trade) {
        log.info("Updating trade : " + trade);
        final Integer id = trade.getTradeId();
        Assert.notNull(id, "trade id should not be null for update");
        Assert.isFound(tradeRepository.existsById(id), "trade requested for update does not exist");
        return tradeRepository.save(trade);
    }

    @Override
    public void deleteTrade(final Integer tradeId) {
        log.info("Deleting by trade id : " + tradeId);
        Assert.notNull(tradeId, "trade id should not be null");
        Assert.isFound(tradeRepository.existsById(tradeId), "trade requested for delete does not exist");
        tradeRepository.deleteById(tradeId);
    }

}
