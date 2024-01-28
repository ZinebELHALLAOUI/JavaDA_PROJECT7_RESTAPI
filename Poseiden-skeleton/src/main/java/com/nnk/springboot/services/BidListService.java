package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;
import java.util.Optional;

public interface BidListService {
    List<BidList> findAllBids();

    Optional<BidList> findBidById(int bidId);

    BidList createBid(BidList bidList);

    BidList updateBid(int id, BidList bidList);


    void deleteBid(int bidId);
}
