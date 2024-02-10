package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class RatingServiceImplTest {

    private final RatingRepository ratingRepository = Mockito.mock(RatingRepository.class);
    private final RatingService ratingService = new RatingServiceImpl(ratingRepository);

    @Test
    public void testFindAllRatings() {
        // Mocking the repository
        List<Rating> expectedRatings = new ArrayList<>();
        when(ratingRepository.findAll()).thenReturn(expectedRatings);

        // Calling service method
        List<Rating> result = ratingService.findAllRatings();

        // Verifying the result
        assertEquals(expectedRatings, result);
    }

    @Test
    public void testFindRatingById() {
        // Mocking the repository
        int ratingId = 1;
        Rating expectedRating = new Rating();
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(expectedRating));

        // Calling service method
        Optional<Rating> result = ratingService.findRatingById(ratingId);

        // Verifying the result
        assertTrue(result.isPresent());
        assertEquals(expectedRating, result.get());
    }

    @Test
    public void testCreateRating() {
        // Mocking the repository
        Rating rating = new Rating();
        when(ratingRepository.save(rating)).thenReturn(rating);

        // Calling service method
        Rating result = ratingService.createRating(rating);

        // Verifying the result
        assertEquals(rating, result);
    }

    @Test
    public void testUpdateRating() {
        // Mocking the repository
        int ratingId = 1;
        Rating updatedRating = new Rating();
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(new Rating()));
        when(ratingRepository.saveAndFlush(updatedRating)).thenReturn(updatedRating);

        // Calling service method
        Rating result = ratingService.updateRating(ratingId, updatedRating);

        // Verifying the result
        assertEquals(updatedRating, result);
    }

    @Test
    public void testDeleteRating() {
        // Mocking the repository
        int ratingId = 1;
        when(ratingRepository.existsById(ratingId)).thenReturn(true);

        // Calling service method
        ratingService.deleteRating(ratingId);

        // Verifying the delete operation
        Mockito.verify(ratingRepository).deleteById(ratingId);
    }
}
