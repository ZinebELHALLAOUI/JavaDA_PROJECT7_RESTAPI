package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    @Test
    @WithMockUser(authorities = "USER")
    public void testHome() throws Exception {
        List<CurvePoint> curvePoints = Arrays.asList(new CurvePoint(), new CurvePoint()); // Remplacez par vos données de test
        when(curvePointService.findAllCurvePoints()).thenReturn(curvePoints);

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("curvePoints", hasSize(2)));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testAddCurvePointForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testValidate() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("term", "1")
                        .param("value", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testShowUpdateForm() throws Exception {
        when(curvePointService.findCurvePointById(1)).thenReturn(Optional.of(new CurvePoint()));

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testUpdateCurvePoint() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "1")
                        .param("term", "1")
                        .param("value", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testDeleteCurvePoint() throws Exception {
        doNothing().when(curvePointService).deleteCurvePoint(1);

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
}