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

@Controller
@AllArgsConstructor
@Slf4j
public class TradeController {

    private final TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        List<Trade> allTrades = tradeService.findAllTrades();
        model.addAttribute("trades", allTrades);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

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

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("trade", tradeService.findTradeById(id).get());
        ;
        return "trade/update";
    }

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

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}
