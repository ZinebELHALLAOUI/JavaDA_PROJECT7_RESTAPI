package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;
import java.util.Optional;

public interface BidListService {
    List<BidList> findAllBids();

    Optional<BidList> findBidById(Integer bidId);

    BidList createBid(BidList bidList);

    BidList updateBid(BidList bidList);


    void deleteBid(Integer bidId);
}
