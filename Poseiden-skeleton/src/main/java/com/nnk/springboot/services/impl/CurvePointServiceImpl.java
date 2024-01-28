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

@Service
@AllArgsConstructor
@Slf4j
public class CurvePointServiceImpl implements CurvePointService {

    private final CurvePointRepository curvePointRepository;

    @Override
    public List<CurvePoint> findAllCurvePoints() {
        log.info("Find all Curve Points");
        return curvePointRepository.findAll();
    }

    @Override
    public Optional<CurvePoint> findCurvePointById(final int curvePointId) {
        log.info("Find curve Point by id : " + curvePointId);
        return curvePointRepository.findById(curvePointId);
    }

    @Override
    public CurvePoint createCurvePoint(final CurvePoint curvePoint) {
        log.info("Creating curve Point : " + curvePoint);
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint updateCurvePoint(int id, CurvePoint curvePoint) {
        log.info("Updating curve Point : " + curvePoint);
        CurvePoint curvePointFound = curvePointRepository.findById(id).orElseThrow(() -> new NotFoundException("Curve Point does not exist"));
        curvePointFound.setTerm(curvePoint.getTerm());
        curvePointFound.setValue(curvePoint.getValue());
        return curvePointRepository.saveAndFlush(curvePointFound);
    }

    @Override
    public void deleteCurvePoint(final int curvePointId) {
        log.info("Deleting by curve Point id : " + curvePointId);
        Assert.isFound(curvePointRepository.existsById(curvePointId), "curve Point requested for delete does not exist");
        curvePointRepository.deleteById(curvePointId);
    }
}
