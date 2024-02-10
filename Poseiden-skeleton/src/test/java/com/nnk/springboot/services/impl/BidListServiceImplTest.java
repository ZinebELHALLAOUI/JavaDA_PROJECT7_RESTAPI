package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class BidListServiceImplTest {

    private final BidListRepository bidListRepository = Mockito.mock(BidListRepository.class);
    private final BidListService bidListService = new BidListServiceImpl(bidListRepository);

    @Test
    public void testFindAllBids() {
        // Mocking the repository
        List<BidList> expectedBids = new ArrayList<>();
        when(bidListRepository.findAll()).thenReturn(expectedBids);

        // Calling service method
        List<BidList> result = bidListService.findAllBids();

        // Verifying the result
        assertEquals(expectedBids, result);
    }

    @Test
    public void testFindBidById() {
        // Mocking the repository
        int bidId = 1;
        BidList expectedBid = new BidList();
        when(bidListRepository.findById(bidId)).thenReturn(Optional.of(expectedBid));

        // Calling service method
        Optional<BidList> result = bidListService.findBidById(bidId);

        // Verifying the result
        assertTrue(result.isPresent());
        assertEquals(expectedBid, result.get());
    }

    @Test
    public void testCreateBid() {
        // Mocking the repository
        BidList bid = new BidList();
        when(bidListRepository.save(bid)).thenReturn(bid);

        // Calling service method
        BidList result = bidListService.createBid(bid);

        // Verifying the result
        assertEquals(bid, result);
    }

    @Test
    public void testUpdateBid() {
        // Mocking the repository
        int bidId = 1;
        BidList updatedBid = new BidList();
        when(bidListRepository.findById(bidId)).thenReturn(Optional.of(new BidList()));
        when(bidListRepository.saveAndFlush(updatedBid)).thenReturn(updatedBid);

        // Calling service method
        BidList result = bidListService.updateBid(bidId, updatedBid);

        // Verifying the result
        assertEquals(updatedBid, result);
    }

    @Test
    public void testDeleteBid() {
        // Mocking the repository
        int bidId = 1;
        when(bidListRepository.existsById(bidId)).thenReturn(true);

        // Calling service method
        bidListService.deleteBid(bidId);

        // Verifying the delete operation
        Mockito.verify(bidListRepository).deleteById(bidId);
    }
}