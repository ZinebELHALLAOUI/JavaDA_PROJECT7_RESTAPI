package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CurvePointServiceImplTest {

    private final CurvePointRepository curvePointRepository = mock(CurvePointRepository.class);
    private final CurvePointServiceImpl curvePointService = new CurvePointServiceImpl(curvePointRepository);

    @Test
    void testFindAllCurvePoints() {
        CurvePoint sampleCurvePoint = new CurvePoint();
        sampleCurvePoint.setId(1);
        sampleCurvePoint.setCurveId(2);

        List<CurvePoint> curvePoints = new ArrayList<>();
        curvePoints.add(sampleCurvePoint);

        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        List<CurvePoint> result = curvePointService.findAllCurvePoints();

        assertEquals(1, result.size());
        assertEquals(sampleCurvePoint, result.get(0));

        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    void testFindCurvePointById() {
        CurvePoint sampleCurvePoint = new CurvePoint();
        sampleCurvePoint.setId(1);
        sampleCurvePoint.setCurveId(2);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(sampleCurvePoint));

        Optional<CurvePoint> result = curvePointService.findCurvePointById(1);

        assertEquals(Optional.of(sampleCurvePoint), result);

        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    void testSaveCurvePoint() {
        CurvePoint sampleCurvePoint = new CurvePoint();
        sampleCurvePoint.setId(1);
        sampleCurvePoint.setCurveId(2);

        when(curvePointRepository.save(sampleCurvePoint)).thenReturn(sampleCurvePoint);

        CurvePoint result = curvePointService.createCurvePoint(sampleCurvePoint);

        assertEquals(sampleCurvePoint, result);

        verify(curvePointRepository, times(1)).save(sampleCurvePoint);
    }

    @Test
    void testDeleteCurvePoint() {
        curvePointService.deleteCurvePoint(1);

        verify(curvePointRepository, times(1)).deleteById(1);
    }
}