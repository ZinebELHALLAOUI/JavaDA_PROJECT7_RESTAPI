package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CurvePointServiceImpl implements CurvePointService {

    private final CurvePointRepository curvePointRepository;

    @Override
    public List<CurvePoint> findAllCurvePoints() {
        return curvePointRepository.findAll();
    }

    @Override
    public Optional<CurvePoint> findCurvePointById(Integer curvePointId) {
        return curvePointRepository.findById(curvePointId);
    }

    @Override
    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public void deleteCurvePoint(Integer curvePointId) {
        curvePointRepository.deleteById(curvePointId);
    }
}
