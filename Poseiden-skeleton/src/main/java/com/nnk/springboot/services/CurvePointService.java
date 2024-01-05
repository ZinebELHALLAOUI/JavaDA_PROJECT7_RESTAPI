package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

public interface CurvePointService {

    List<CurvePoint> findAllCurvePoints();

    Optional<CurvePoint> findCurvePointById(Integer curvePointId);

    CurvePoint saveCurvePoint(CurvePoint curvePoint);

    void deleteCurvePoint(Integer curvePointId);
}
