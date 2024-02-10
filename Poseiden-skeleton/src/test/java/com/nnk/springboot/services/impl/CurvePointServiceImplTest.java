package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CurvePointServiceImplTest {

    private final CurvePointRepository curvePointRepository = Mockito.mock(CurvePointRepository.class);
    private final CurvePointService curvePointService = new CurvePointServiceImpl(curvePointRepository);

    @Test
    public void testFindAllCurvePoints() {
        // Mocking the repository
        List<CurvePoint> expectedCurvePoints = new ArrayList<>();
        when(curvePointRepository.findAll()).thenReturn(expectedCurvePoints);

        // Calling service method
        List<CurvePoint> result = curvePointService.findAllCurvePoints();

        // Verifying the result
        assertEquals(expectedCurvePoints, result);
    }

    @Test
    public void testFindCurvePointById() {
        // Mocking the repository
        int curvePointId = 1;
        CurvePoint expectedCurvePoint = new CurvePoint();
        when(curvePointRepository.findById(curvePointId)).thenReturn(Optional.of(expectedCurvePoint));

        // Calling service method
        Optional<CurvePoint> result = curvePointService.findCurvePointById(curvePointId);

        // Verifying the result
        assertTrue(result.isPresent());
        assertEquals(expectedCurvePoint, result.get());
    }

    @Test
    public void testCreateCurvePoint() {
        // Mocking the repository
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        // Calling service method
        CurvePoint result = curvePointService.createCurvePoint(curvePoint);

        // Verifying the result
        assertEquals(curvePoint, result);
    }

    @Test
    public void testUpdateCurvePoint() {
        // Mocking the repository
        int curvePointId = 1;
        CurvePoint updatedCurvePoint = new CurvePoint();
        when(curvePointRepository.findById(curvePointId)).thenReturn(Optional.of(new CurvePoint()));
        when(curvePointRepository.saveAndFlush(updatedCurvePoint)).thenReturn(updatedCurvePoint);

        // Calling service method
        CurvePoint result = curvePointService.updateCurvePoint(curvePointId, updatedCurvePoint);

        // Verifying the result
        assertEquals(updatedCurvePoint, result);
    }

    @Test
    public void testDeleteCurvePoint() {
        // Mocking the repository
        int curvePointId = 1;
        when(curvePointRepository.existsById(curvePointId)).thenReturn(true);

        // Calling service method
        curvePointService.deleteCurvePoint(curvePointId);

        // Verifying the delete operation
        verify(curvePointRepository).deleteById(curvePointId);
    }
}