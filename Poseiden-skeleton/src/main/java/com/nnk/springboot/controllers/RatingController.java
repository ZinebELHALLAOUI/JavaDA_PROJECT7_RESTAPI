package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.Assert;
import com.nnk.springboot.services.RatingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Contrôleur gérant les opérations liées aux évaluations (ratings).
 */
@Controller
@AllArgsConstructor
@Slf4j
public class RatingController {

    private final RatingService ratingService;

    /**
     * Affiche la liste de toutes les évaluations.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue affichant la liste des évaluations.
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        List<Rating> ratings = ratingService.findAllRatings();
        model.addAttribute("ratings", ratings);
        return "rating/list";
    }

    /**
     * Affiche le formulaire d'ajout d'une nouvelle évaluation.
     *
     * @param rating L'évaluation à ajouter.
     * @return La vue du formulaire d'ajout.
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Valide et crée une nouvelle évaluation.
     *
     * @param rating L'évaluation à valider et créer.
     * @param result Le résultat de la validation.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue appropriée en cas d'erreur ou redirection vers la liste des évaluations.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Errors: " + result.getAllErrors());
            return "rating/add";
        }
        Assert.isNull(rating.getId(), "rating id should be null for creation");
        ratingService.createRating(rating);
        return "redirect:/rating/list";
    }

    /**
     * Affiche le formulaire de mise à jour d'une évaluation spécifique.
     *
     * @param id L'identifiant de l'évaluation à mettre à jour.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue du formulaire de mise à jour.
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("rating", ratingService.findRatingById(id).get());
        return "rating/update";
    }

    /**
     * Valide et met à jour une évaluation existante.
     *
     * @param id L'identifiant de l'évaluation à mettre à jour.
     * @param rating L'évaluation mise à jour.
     * @param result Le résultat de la validation.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue appropriée en cas d'erreur ou redirection vers la liste des évaluations.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Errors: " + result.getAllErrors());
            return "rating/update";
        }
        rating.setId(id);
        ratingService.updateRating(id, rating);
        return "redirect:/rating/list";
    }

    /**
     * Supprime une évaluation spécifique.
     *
     * @param id L'identifiant de l'évaluation à supprimer.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La redirection vers la liste des évaluations après la suppression.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
