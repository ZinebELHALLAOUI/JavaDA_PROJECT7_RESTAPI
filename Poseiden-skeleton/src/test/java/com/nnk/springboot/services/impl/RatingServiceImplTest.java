package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RatingServiceImplTest {

    private final RatingRepository ratingRepository = mock(RatingRepository.class);
    private final RatingServiceImpl ratingService = new RatingServiceImpl(ratingRepository);

    @Test
    void testFindAllRatings() {
        Rating sampleRating = new Rating();
        sampleRating.setId(1);
        sampleRating.setMoodysRating("A1");

        List<Rating> ratings = new ArrayList<>();
        ratings.add(sampleRating);

        when(ratingRepository.findAll()).thenReturn(ratings);

        List<Rating> result = ratingService.findAllRatings();

        assertEquals(1, result.size());
        assertEquals(sampleRating, result.get(0));

        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void testFindRatingById() {
        Rating sampleRating = new Rating();
        sampleRating.setId(1);
        sampleRating.setMoodysRating("A1");

        when(ratingRepository.findById(1)).thenReturn(Optional.of(sampleRating));

        Optional<Rating> result = ratingService.findRatingById(1);

        assertEquals(Optional.of(sampleRating), result);

        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    void testSaveRating() {
        // Arrange
        Rating sampleRating = new Rating();
        sampleRating.setId(1);
        sampleRating.setMoodysRating("A1");

        when(ratingRepository.save(sampleRating)).thenReturn(sampleRating);

        Rating result = ratingService.saveRating(sampleRating);

        assertEquals(sampleRating, result);

        verify(ratingRepository, times(1)).save(sampleRating);
    }

    @Test
    void testDeleteRating() {
        ratingService.deleteRating(1);

        verify(ratingRepository, times(1)).deleteById(1);
    }
}