package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    public List<Rating> findAllRatings() {
        return ratingRepository.findAll();
    }

    @Override

    public Optional<Rating> findRatingById(Integer ratingId) {
        return ratingRepository.findById(ratingId);
    }

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Integer ratingId) {
        ratingRepository.deleteById(ratingId);
    }

}
