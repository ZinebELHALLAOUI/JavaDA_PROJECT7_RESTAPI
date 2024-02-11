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

/**
 * Implémentation du service gérant les opérations liées aux transactions (Trade).
 */
@Service
@AllArgsConstructor
@Slf4j
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    /**
     * Récupère la liste de toutes les transactions.
     *
     * @return La liste de toutes les transactions.
     */
    @Override
    public List<Trade> findAllTrades() {
        log.info("Find all trades");
        return tradeRepository.findAll();
    }

    /**
     * Récupère une transaction par son identifiant.
     *
     * @param tradeId L'identifiant de la transaction à récupérer.
     * @return Une option contenant la transaction correspondante s'il existe.
     */
    @Override
    public Optional<Trade> findTradeById(final int tradeId) {
        log.info("Find trade by id : " + tradeId);
        return tradeRepository.findById(tradeId);
    }

    /**
     * Crée une nouvelle transaction.
     *
     * @param trade La transaction à créer.
     * @return La transaction créée.
     */
    @Override
    public Trade createTrade(final Trade trade) {
        log.info("Creating trade : " + trade);
        return tradeRepository.save(trade);
    }

    /**
     * Met à jour une transaction existante.
     *
     * @param id L'identifiant de la transaction à mettre à jour.
     * @param trade La transaction mise à jour.
     * @return La transaction mise à jour.
     * @throws NotFoundException si la transaction n'est pas trouvée.
     */
    @Override
    public Trade updateTrade(int id, final Trade trade) {
        log.info("Updating trade : " + trade);
        Trade tradeFound = tradeRepository.findById(id).orElseThrow(() -> new NotFoundException("Trade does not exist"));
        tradeFound.setAccount(trade.getAccount());
        tradeFound.setType(trade.getType());
        tradeFound.setBuyQuantity(trade.getBuyQuantity());
        return tradeRepository.saveAndFlush(tradeFound);
    }

    /**
     * Supprime une transaction par son identifiant.
     *
     * @param tradeId L'identifiant de la transaction à supprimer.
     * @throws NotFoundException si la transaction n'est pas trouvée.
     */
    @Override
    public void deleteTrade(final int tradeId) {
        log.info("Deleting by trade id : " + tradeId);
        Assert.isFound(tradeRepository.existsById(tradeId), "trade requested for delete does not exist");
        tradeRepository.deleteById(tradeId);
    }

}
