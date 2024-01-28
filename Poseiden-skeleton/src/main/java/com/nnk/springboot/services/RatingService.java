package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;

import java.util.List;
import java.util.Optional;

public interface RatingService {

    List<Rating> findAllRatings();

    Optional<Rating> findRatingById(int ratingId);

    Rating createRating(Rating rating);

    Rating updateRating(int id, Rating rating);

    void deleteRating(int ratingId);

}
