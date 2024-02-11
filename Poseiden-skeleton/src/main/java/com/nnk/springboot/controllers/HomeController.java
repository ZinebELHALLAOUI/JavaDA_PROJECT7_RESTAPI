package com.nnk.springboot.controllers;

import com.nnk.springboot.services.BidListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrôleur responsable de la gestion des vues liées à la page d'accueil et aux fonctionnalités administratives.
 */
@Controller
@AllArgsConstructor
public class HomeController {

    private final BidListService bidListService;

    /**
     * Affiche la page d'accueil.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue associée à la page d'accueil.
     */
    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }

    /**
     * Redirige vers la liste des offres pour l'interface administrateur.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Redirige vers la liste des offres.
     */
    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        return "redirect:/bidList/list";
    }
}
