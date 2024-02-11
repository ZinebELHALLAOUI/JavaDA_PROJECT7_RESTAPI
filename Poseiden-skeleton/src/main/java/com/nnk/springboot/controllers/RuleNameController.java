package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.Assert;
import com.nnk.springboot.services.RuleNameService;
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
 * Contrôleur gérant les opérations liées aux noms de règles (RuleName).
 */
@Controller
@AllArgsConstructor
@Slf4j
public class RuleNameController {

    private final RuleNameService ruleNameService;

    /**
     * Affiche la liste de tous les noms de règles.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue affichant la liste des noms de règles.
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        List<RuleName> ruleNames = ruleNameService.findAllRuleNames();
        model.addAttribute("ruleNames", ruleNames);
        return "ruleName/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un nouveau nom de règle.
     *
     * @param bid Le nom de règle à ajouter.
     * @return La vue du formulaire d'ajout.
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    /**
     * Valide et crée un nouveau nom de règle.
     *
     * @param ruleName Le nom de règle à valider et créer.
     * @param result Le résultat de la validation.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue appropriée en cas d'erreur ou redirection vers la liste des noms de règles.
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Errors: " + result.getAllErrors());
            return "ruleName/add";
        }
        Assert.isNull(ruleName.getId(), "rule Name id should be null for creation");
        ruleNameService.createRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Affiche le formulaire de mise à jour d'un nom de règle spécifique.
     *
     * @param id L'identifiant du nom de règle à mettre à jour.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue du formulaire de mise à jour.
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ruleName", ruleNameService.findRuleNameById(id).get());
        return "ruleName/update";
    }

    /**
     * Valide et met à jour un nom de règle existant.
     *
     * @param id L'identifiant du nom de règle à mettre à jour.
     * @param ruleName Le nom de règle mis à jour.
     * @param result Le résultat de la validation.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue appropriée en cas d'erreur ou redirection vers la liste des noms de règles.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Errors: " + result.getAllErrors());
            return "ruleName/update";
        }
        ruleNameService.updateRuleName(id, ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Supprime un nom de règle spécifique.
     *
     * @param id L'identifiant du nom de règle à supprimer.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La redirection vers la liste des noms de règles après la suppression.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
