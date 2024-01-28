package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.Assert;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    public List<Rating> findAllRatings() {
        log.info("Find all ratings");
        return ratingRepository.findAll();
    }

    @Override
    public Optional<Rating> findRatingById(final int ratingId) {
        log.info("Find rating by id : " + ratingId);
        return ratingRepository.findById(ratingId);
    }

    @Override
    public Rating createRating(final Rating rating) {
        log.info("Creating rating : " + rating);
        return ratingRepository.save(rating);
    }

    @Override
    public Rating updateRating(int id, Rating rating) {
        log.info("Updating rating : " + rating);
        Rating ratingFound = ratingRepository.findById(id).orElseThrow(() -> new NotFoundException("Rating name does not exist"));
        ratingFound.setMoodysRating(rating.getMoodysRating());
        ratingFound.setSandPRating(rating.getSandPRating());
        ratingFound.setFitchRating(rating.getFitchRating());
        ratingFound.setOrderNumber(rating.getOrderNumber());
        return ratingRepository.saveAndFlush(ratingFound);
    }

    @Override
    public void deleteRating(final int ratingId) {
        log.info("Deleting by rating id : " + ratingId);
        Assert.isFound(ratingRepository.existsById(ratingId), "rating requested for delete does not exist");
        ratingRepository.deleteById(ratingId);
    }

}
