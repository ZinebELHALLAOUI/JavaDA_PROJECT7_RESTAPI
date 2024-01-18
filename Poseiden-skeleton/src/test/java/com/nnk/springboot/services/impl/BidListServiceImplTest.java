package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BidListServiceImplTest {

    private final BidListRepository bidListRepository = mock(BidListRepository.class);

    private final BidListService bidListService = new BidListServiceImpl(bidListRepository);


    @Test
    void testFindAllBids() {
        // Given
        BidList bid1 = new BidList();
        BidList bid2 = new BidList();
        List<BidList> bidList = Arrays.asList(bid1, bid2);

        when(bidListRepository.findAll()).thenReturn(bidList);

        // When
        List<BidList> result = bidListService.findAllBids();

        // Then
        assertEquals(2, result.size());
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    void testFindBidById() {
        // Given
        BidList bid = new BidList();
        bid.setId(1);

        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        // When
        Optional<BidList> result = bidListService.findBidById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        verify(bidListRepository, times(1)).findById(1);
    }

    @Test
    void testSaveBid() {
        // Given
        BidList bid = new BidList();
        when(bidListRepository.save(bid)).thenReturn(bid);

        // When
        BidList result = bidListService.createBid(bid);

        // Then
        assertNotNull(result);
        verify(bidListRepository, times(1)).save(bid);
    }

    @Test
    void testDeleteBid() {
        // Given
        int bidId = 1;

        // When
        bidListService.deleteBid(bidId);

        // Then
        verify(bidListRepository, times(1)).deleteById(bidId);
    }
}