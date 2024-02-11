package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur responsable de la gestion des vues liées à l'authentification et aux autorisations.
 */
@Controller
@RequestMapping("app")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Affiche les détails de tous les articles des utilisateurs sécurisés.
     *
     * @return ModelAndView contenant la liste des utilisateurs et la vue associée.
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Gère la page d'erreur pour les accès non autorisés.
     *
     * @return ModelAndView contenant un message d'erreur et la vue associée à la page d'erreur 403.
     */
    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage = "Vous n'êtes pas autorisé(e) à accéder aux données demandées.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}

