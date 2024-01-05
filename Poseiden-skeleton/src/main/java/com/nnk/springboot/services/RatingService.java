package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {

    List<Rating> findAllRatings();

    Optional<Rating> findRatingById(Integer ratingId);

    Rating saveRating(Rating rating);

    void deleteRating(Integer ratingId);

}
