package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.Assert;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {

    private final CurvePointService curvePointService;

    /**
     * Affiche la liste de tous les points de courbe.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        List<CurvePoint> curvePoints = curvePointService.findAllCurvePoints();
        model.addAttribute("curvePoints", curvePoints);
        return "curvePoint/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un nouveau point de courbe.
     *
     * @param curvePoint L'objet CurvePoint utilisé pour lier les données du formulaire.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * Valide et crée un nouveau point de courbe.
     *
     * @param curvePoint L'objet CurvePoint contenant les données du point de courbe à créer.
     * @param result     Le résultat de la validation des données.
     * @param model      Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher ou une redirection vers la liste des points de courbe.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Errors: " + result.getAllErrors());
            return "curvePoint/add";
        }
        Assert.isNull(curvePoint.getCurveId(), "curve Point id should be null for creation");
        curvePointService.createCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Affiche le formulaire de mise à jour d'un point de courbe spécifique.
     *
     * @param id    L'identifiant du point de courbe à mettre à jour.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue associée au formulaire de mise à jour.
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("curvePoint", curvePointService.findCurvePointById(id).get());
        return "curvePoint/update";
    }

    /**
     * Traite la mise à jour d'un point de courbe spécifique.
     *
     * @param id           L'identifiant du point de courbe à mettre à jour.
     * @param curvePoint   Le point de courbe avec les nouvelles données.
     * @param result       Le résultat de la validation.
     * @param model        Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue de mise à jour en cas d'erreurs de validation, sinon redirige vers la liste des points de courbe.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Erreurs de validation : " + result.getAllErrors());
            return "curvePoint/update";
        }
        curvePointService.updateCurvePoint(id, curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Supprime un point de courbe spécifique.
     *
     * @param id    L'identifiant du point de courbe à supprimer.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Redirige vers la liste des points de courbe après la suppression.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        curvePointService.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }

}
