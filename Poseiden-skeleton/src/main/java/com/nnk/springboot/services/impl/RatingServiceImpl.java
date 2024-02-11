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

/**
 * Implémentation du service gérant les opérations liées aux évaluations de crédit (Rating).
 */
@Service
@AllArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    /**
     * Récupère la liste de toutes les évaluations de crédit.
     *
     * @return La liste de toutes les évaluations de crédit.
     */
    @Override
    public List<Rating> findAllRatings() {
        log.info("Find all ratings");
        return ratingRepository.findAll();
    }

    /**
     * Récupère une évaluation de crédit par son identifiant.
     *
     * @param ratingId L'identifiant de l'évaluation de crédit à récupérer.
     * @return Une option contenant l'évaluation de crédit correspondante s'il existe.
     */
    @Override
    public Optional<Rating> findRatingById(final int ratingId) {
        log.info("Find rating by id : " + ratingId);
        return ratingRepository.findById(ratingId);
    }

    /**
     * Crée une nouvelle évaluation de crédit.
     *
     * @param rating L'évaluation de crédit à créer.
     * @return L'évaluation de crédit créée.
     */
    @Override
    public Rating createRating(final Rating rating) {
        log.info("Creating rating : " + rating);
        return ratingRepository.save(rating);
    }

    /**
     * Met à jour une évaluation de crédit existante.
     *
     * @param id L'identifiant de l'évaluation de crédit à mettre à jour.
     * @param rating L'évaluation de crédit mise à jour.
     * @return L'évaluation de crédit mise à jour.
     * @throws NotFoundException si l'évaluation de crédit n'est pas trouvée.
     */
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

    /**
     * Supprime une évaluation de crédit par son identifiant.
     *
     * @param ratingId L'identifiant de l'évaluation de crédit à supprimer.
     * @throws NotFoundException si l'évaluation de crédit n'est pas trouvée.
     */
    @Override
    public void deleteRating(final int ratingId) {
        log.info("Deleting by rating id : " + ratingId);
        Assert.isFound(ratingRepository.existsById(ratingId), "rating requested for delete does not exist");
        ratingRepository.deleteById(ratingId);
    }
}
