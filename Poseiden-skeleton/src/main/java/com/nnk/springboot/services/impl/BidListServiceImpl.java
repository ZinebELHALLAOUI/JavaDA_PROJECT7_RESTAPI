package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.exceptions.Assert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;

    @Override
    public List<BidList> findAllBids() {
        log.info("Find all Bids");
        return bidListRepository.findAll();
    }

    @Override
    public Optional<BidList> findBidById(final Integer bidId) {
        log.info("Find Bid by id : " + bidId);
        Assert.notNull(bidId, "Bid id should not be null");
        return bidListRepository.findById(bidId);
    }

    @Override
    public BidList createBid(final BidList bidList) {
        log.info("Creating Bid : "+ bidList);
        Assert.isNull(bidList.getBidListId(), "Bid id should be null for creation");
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList updateBid(final BidList bidList) {
        log.info("Updating Bid : "+ bidList);
        final Integer bidId = bidList.getBidListId();
        Assert.notNull(bidId, "Bid id should not be null for update");
        Assert.isFound(bidListRepository.existsById(bidId), "Bid requested for update does not exist");
        return bidListRepository.save(bidList);
    }

    @Override
    public void deleteBid(Integer bidId) {
        log.info("Deleting by bid id : " + bidId);
        Assert.notNull(bidId, "Bid id should not be null");
        Assert.isFound(bidListRepository.existsById(bidId), "Bid requested for delete does not exist");
        bidListRepository.deleteById(bidId);
    }

}