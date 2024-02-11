package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.Assert;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service gérant les opérations liées aux points de courbe (CurvePoint).
 */
@Service
@AllArgsConstructor
@Slf4j
public class CurvePointServiceImpl implements CurvePointService {

    private final CurvePointRepository curvePointRepository;

    /**
     * Récupère la liste de tous les points de courbe.
     *
     * @return La liste de tous les points de courbe.
     */
    @Override
    public List<CurvePoint> findAllCurvePoints() {
        log.info("Find all Curve Points");
        return curvePointRepository.findAll();
    }

    /**
     * Récupère un point de courbe par son identifiant.
     *
     * @param curvePointId L'identifiant du point de courbe à récupérer.
     * @return Une option contenant le point de courbe correspondant s'il existe.
     */
    @Override
    public Optional<CurvePoint> findCurvePointById(final int curvePointId) {
        log.info("Find curve Point by id : " + curvePointId);
        return curvePointRepository.findById(curvePointId);
    }

    /**
     * Crée un nouveau point de courbe.
     *
     * @param curvePoint Le point de courbe à créer.
     * @return Le point de courbe créé.
     */
    @Override
    public CurvePoint createCurvePoint(final CurvePoint curvePoint) {
        log.info("Creating curve Point : " + curvePoint);
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Met à jour un point de courbe existant.
     *
     * @param id L'identifiant du point de courbe à mettre à jour.
     * @param curvePoint Le point de courbe mis à jour.
     * @return Le point de courbe mis à jour.
     * @throws NotFoundException si le point de courbe n'est pas trouvé.
     */
    @Override
    public CurvePoint updateCurvePoint(int id, CurvePoint curvePoint) {
        log.info("Updating curve Point : " + curvePoint);
        CurvePoint curvePointFound = curvePointRepository.findById(id).orElseThrow(() -> new NotFoundException("Curve Point does not exist"));
        curvePointFound.setTerm(curvePoint.getTerm());
        curvePointFound.setValue(curvePoint.getValue());
        return curvePointRepository.saveAndFlush(curvePointFound);
    }

    /**
     * Supprime un point de courbe par son identifiant.
     *
     * @param curvePointId L'identifiant du point de courbe à supprimer.
     * @throws NotFoundException si le point de courbe n'est pas trouvé.
     */
    @Override
    public void deleteCurvePoint(final int curvePointId) {
        log.info("Deleting by curve Point id : " + curvePointId);
        Assert.isFound(curvePointRepository.existsById(curvePointId), "curve Point requested for delete does not exist");
        curvePointRepository.deleteById(curvePointId);
    }
}
