package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.exceptions.Assert;
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
    public Optional<Rating> findRatingById(final Integer ratingId) {
        log.info("Find rating by id : " + ratingId);
        Assert.notNull(ratingId, "rating id should not be null");
        return ratingRepository.findById(ratingId);
    }

    @Override
    public Rating createRating(final Rating rating) {
        log.info("Creating rating : " + rating);
        Assert.isNull(rating.getId(), "rating id should be null for creation");
        return ratingRepository.save(rating);
    }

    @Override
    public Rating updateRating(final Rating rating) {
        log.info("Updating rating : " + rating);
        final Integer id = rating.getId();
        Assert.notNull(id, "rating id should not be null for update");
        Assert.isFound(ratingRepository.existsById(id), "rating requested for update does not exist");
        return ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(final Integer ratingId) {
        log.info("Deleting by rating id : " + ratingId);
        Assert.notNull(ratingId, "rating id should not be null");
        Assert.isFound(ratingRepository.existsById(ratingId), "rating requested for delete does not exist");
        ratingRepository.deleteById(ratingId);
    }

}
