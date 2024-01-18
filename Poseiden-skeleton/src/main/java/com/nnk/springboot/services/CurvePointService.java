package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

public interface CurvePointService {

    List<CurvePoint> findAllCurvePoints();

    Optional<CurvePoint> findCurvePointById(int curvePointId);

    CurvePoint createCurvePoint(CurvePoint curvePoint);

    CurvePoint updateCurvePoint(CurvePoint curvePoint);

    void deleteCurvePoint(int curvePointId);
}
