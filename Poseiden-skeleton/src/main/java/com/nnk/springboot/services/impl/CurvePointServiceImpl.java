package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.exceptions.Assert;
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
    public Optional<CurvePoint> findCurvePointById(final Integer curvePointId) {
        log.info("Find curve Point by id : " + curvePointId);
        Assert.notNull(curvePointId, "curve Point id should not be null");
        return curvePointRepository.findById(curvePointId);
    }

    @Override
    public CurvePoint createCurvePoint(final CurvePoint curvePoint) {
        log.info("Creating curve Point : " + curvePoint);
        Assert.isNull(curvePoint.getCurveId(), "curve Point id should be null for creation");
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint updateCurvePoint(CurvePoint curvePoint) {
        log.info("Updating curve Point : " + curvePoint);
        final Integer id = curvePoint.getCurveId();
        Assert.notNull(id, "curve Point id should not be null for update");
        Assert.isFound(curvePointRepository.existsById(id), "curve Point requested for update does not exist");
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public void deleteCurvePoint(final Integer curvePointId) {
        log.info("Deleting by curve Point id : " + curvePointId);
        Assert.notNull(curvePointId, "curve Point id should not be null");
        Assert.isFound(curvePointRepository.existsById(curvePointId), "curve Point requested for delete does not exist");
        curvePointRepository.deleteById(curvePointId);
    }
}
