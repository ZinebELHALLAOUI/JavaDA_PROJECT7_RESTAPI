package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;

    @Override
    public List<BidList> findAllBids() {
        return bidListRepository.findAll();
    }

    @Override
    public Optional<BidList> findBidById(Integer bidId) {
        return bidListRepository.findById(bidId);
    }

    @Override
    public BidList saveBid(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    @Override
    public void deleteBid(Integer bidId) {
        bidListRepository.deleteById(bidId);
    }

}