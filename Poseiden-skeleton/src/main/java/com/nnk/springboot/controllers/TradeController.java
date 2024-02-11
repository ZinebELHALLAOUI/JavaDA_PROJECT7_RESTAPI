package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.Assert;
import com.nnk.springboot.services.TradeService;
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
 * Contrôleur gérant les opérations liées aux transactions (Trade).
 */
@Controller
@AllArgsConstructor
@Slf4j
public class TradeController {

    private final TradeService tradeService;

    /**
     * Affiche la liste de toutes les transactions.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue affichant la liste des transactions.
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        List<Trade> allTrades = tradeService.findAllTrades();
        model.addAttribute("trades", allTrades);
        return "trade/list";
    }

    /**
     * Affiche le formulaire d'ajout d'une nouvelle transaction.
     *
     * @param bid La transaction à ajouter.
     * @return La vue du formulaire d'ajout.
     */
    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    /**
     * Valide et crée une nouvelle transaction.
     *
     * @param trade La transaction à valider et créer.
     * @param result Le résultat de la validation.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue appropriée en cas d'erreur ou redirection vers la liste des transactions.
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Errors: " + result.getAllErrors());
            return "trade/add";
        }
        Assert.isNull(trade.getTradeId(), "trade id should be null for creation");
        tradeService.createTrade(trade);
        return "redirect:/trade/list";
    }

    /**
     * Affiche le formulaire de mise à jour d'une transaction spécifique.
     *
     * @param id L'identifiant de la transaction à mettre à jour.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue du formulaire de mise à jour.
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("trade", tradeService.findTradeById(id).get());
        return "trade/update";
    }

    /**
     * Valide et met à jour une transaction existante.
     *
     * @param id L'identifiant de la transaction à mettre à jour.
     * @param trade La transaction mise à jour.
     * @param result Le résultat de la validation.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La vue appropriée en cas d'erreur ou redirection vers la liste des transactions.
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Errors: " + result.getAllErrors());
            return "trade/update";
        }
        tradeService.updateTrade(id, trade);
        return "redirect:/trade/list";
    }

    /**
     * Supprime une transaction spécifique.
     *
     * @param id L'identifiant de la transaction à supprimer.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return La redirection vers la liste des transactions après la suppression.
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}
