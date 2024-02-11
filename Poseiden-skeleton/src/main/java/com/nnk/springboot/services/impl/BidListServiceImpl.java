package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.Assert;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service gérant les opérations liées aux enchères (BidList).
 */
@Service
@AllArgsConstructor
@Slf4j
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;

    /**
     * Récupère la liste de toutes les enchères.
     *
     * @return La liste de toutes les enchères.
     */
    @Override
    public List<BidList> findAllBids() {
        log.info("Find all Bids");
        return bidListRepository.findAll();
    }

    /**
     * Récupère une enchère par son identifiant.
     *
     * @param bidId L'identifiant de l'enchère à récupérer.
     * @return Une option contenant l'enchère correspondante s'il existe.
     */
    @Override
    public Optional<BidList> findBidById(final int bidId) {
        log.info("Find Bid by id : " + bidId);
        return bidListRepository.findById(bidId);
    }

    /**
     * Crée une nouvelle enchère.
     *
     * @param bidList L'enchère à créer.
     * @return L'enchère créée.
     */
    @Override
    public BidList createBid(final BidList bidList) {
        log.info("Creating Bid : " + bidList);
        return bidListRepository.save(bidList);
    }

    /**
     * Met à jour une enchère existante.
     *
     * @param id L'identifiant de l'enchère à mettre à jour.
     * @param bidList L'enchère mise à jour.
     * @return L'enchère mise à jour.
     * @throws NotFoundException si l'enchère n'est pas trouvée.
     */
    @Override
    public BidList updateBid(int id, final BidList bidList) {
        log.info("Updating Bid : " + bidList);
        BidList bidFound = bidListRepository.findById(id).orElseThrow(() -> new NotFoundException("Bid does not exist"));
        bidFound.setAccount(bidList.getAccount());
        bidFound.setType(bidList.getType());
        bidFound.setBidQuantity(bidList.getBidQuantity());
        return bidListRepository.saveAndFlush(bidList);
    }

    /**
     * Supprime une enchère par son identifiant.
     *
     * @param bidId L'identifiant de l'enchère à supprimer.
     * @throws NotFoundException si l'enchère n'est pas trouvée.
     */
    @Override
    public void deleteBid(int bidId) {
        log.info("Deleting by bid id : " + bidId);
        Assert.isFound(bidListRepository.existsById(bidId), "Bid requested for delete does not exist");
        bidListRepository.deleteById(bidId);
    }
}
