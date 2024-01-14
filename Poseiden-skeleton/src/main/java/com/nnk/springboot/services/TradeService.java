package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

import java.util.List;
import java.util.Optional;

public interface TradeService {

    List<Trade> findAllTrades();

    Optional<Trade> findTradeById(Integer tradeId);

    Trade createTrade(Trade trade);

    Trade updateTrade(Trade trade);

    void deleteTrade(Integer tradeId);
}
