package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.exceptions.Assert;
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
    public Optional<Trade> findTradeById(final int tradeId) {
        log.info("Find trade by id : " + tradeId);
        return tradeRepository.findById(tradeId);
    }

    @Override
    public Trade createTrade(final Trade trade) {
        log.info("Creating trade : " + trade);
        return tradeRepository.save(trade);
    }

    @Override
    public Trade updateTrade(int id, final Trade trade) {
        log.info("Updating trade : " + trade);
        Trade tradeFound = tradeRepository.findById(id).orElseThrow(() -> new NotFoundException("Trade does not exist"));
        tradeFound.setAccount(trade.getAccount());
        tradeFound.setType(trade.getType());
        tradeFound.setBuyPrice(trade.getBuyQuantity());
        return tradeRepository.saveAndFlush(tradeFound);
    }

    @Override
    public void deleteTrade(final int tradeId) {
        log.info("Deleting by trade id : " + tradeId);
        Assert.isFound(tradeRepository.existsById(tradeId), "trade requested for delete does not exist");
        tradeRepository.deleteById(tradeId);
    }

}
