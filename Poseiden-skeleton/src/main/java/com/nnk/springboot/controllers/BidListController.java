package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.Assert;
import com.nnk.springboot.services.BidListService;
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


@Controller
@AllArgsConstructor
@Slf4j
public class BidListController {

    private final BidListService bidListService;

    /**
     * Affiche la liste de toutes les offres.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        List<BidList> allBids = bidListService.findAllBids();
        model.addAttribute("bidLists", allBids);
        return "bidList/list";
    }

    /**
     * Affiche le formulaire d'ajout d'une nouvelle offre.
     *
     * @param bid L'objet BidList utilisé pour lier les données du formulaire.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /**
     * Valide et crée une nouvelle offre.
     *
     * @param bid    L'objet BidList contenant les données de l'offre à créer.
     * @param result Le résultat de la validation des données.
     * @param model  Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher ou une redirection vers la liste des offres.
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Erreurs de validation : " + result.getAllErrors());
            return "bidList/add";
        }
        Assert.isNull(bid.getId(), "L'identifiant de l'offre doit être nul pour la création");
        bidListService.createBid(bid);
        return "redirect:/bidList/list";
    }

    /**
     * Affiche le formulaire de mise à jour d'une offre.
     *
     * @param id    L'identifiant de l'offre à mettre à jour.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("bidList", bidListService.findBidById(id).get());
        return "bidList/update";
    }

    /**
     * Valide et met à jour une offre existante.
     *
     * @param id      L'identifiant de l'offre à mettre à jour.
     * @param bidList L'objet BidList contenant les données de l'offre mise à jour.
     * @param result  Le résultat de la validation des données.
     * @param model   Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher ou une redirection vers la liste des offres.
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Erreurs de validation : " + result.getAllErrors());
            return "bidList/update";
        }
        bidListService.updateBid(id, bidList);
        return "redirect:/bidList/list";
    }

    /**
     * Supprime une offre existante.
     *
     * @param id    L'identifiant de l'offre à supprimer.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Une redirection vers la liste des offres.
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBid(id);
        return "redirect:/bidList/list";
    }
}
