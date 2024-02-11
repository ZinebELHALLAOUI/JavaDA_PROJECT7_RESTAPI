package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.Assert;
import com.nnk.springboot.services.UserService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Contrôleur gérant les opérations liées aux utilisateurs (User).
 */
@Controller
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Affiche la liste de tous les utilisateurs.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue affichant la liste des utilisateurs.
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un nouvel utilisateur.
     *
     * @param bid L'utilisateur à ajouter.
     * @return La vue du formulaire d'ajout.
     */
    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    /**
     * Valide et crée un nouvel utilisateur.
     *
     * @param user L'utilisateur à valider et créer.
     * @param result Le résultat de la validation.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @param redirectAttributes Les attributs de redirection pour transmettre des informations flash.
     * @return La vue appropriée en cas d'erreur ou redirection vers la liste des utilisateurs.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Assert.isNull(user.getId(), "User id should be null for creation");
        if (!result.hasErrors()) {
            try {
                userService.createUser(user);
            } catch (IllegalArgumentException exception) {
                result.rejectValue("username", "username.invalid", "user already exists");
                return "user/add";
            }
            final List<String> infos = List.of("Request succeed");
            redirectAttributes.addFlashAttribute("infos", infos);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * Affiche le formulaire de mise à jour d'un utilisateur spécifique.
     *
     * @param id L'identifiant de l'utilisateur à mettre à jour.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue du formulaire de mise à jour.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Valide et met à jour un utilisateur existant.
     *
     * @param id L'identifiant de l'utilisateur à mettre à jour.
     * @param user L'utilisateur mis à jour.
     * @param result Le résultat de la validation.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @param redirectAttributes Les attributs de redirection pour transmettre des informations flash.
     * @return La vue appropriée en cas d'erreur ou redirection vers la liste des utilisateurs.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            log.error("Errors: " + result.getAllErrors());
            return "user/update";
        }
        userService.updateUser(id, user);
        model.addAttribute("users", userService.findAll());
        final List<String> infos = List.of("Request succeed");
        redirectAttributes.addFlashAttribute("infos", infos);
        return "redirect:/user/list";
    }

    /**
     * Supprime un utilisateur spécifique.
     *
     * @param id L'identifiant de l'utilisateur à supprimer.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @param redirectAttributes Les attributs de redirection pour transmettre des informations flash.
     * @return La redirection vers la liste des utilisateurs après la suppression.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        model.addAttribute("users", userService.findAll());
        final List<String> infos = List.of("Request succeed");
        redirectAttributes.addFlashAttribute("infos", infos);
        return "redirect:/user/list";
    }
}
