package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Test
    @WithMockUser(authorities = "USER")
    public void testHome() throws Exception {
        List<Rating> ratings = Arrays.asList(new Rating(), new Rating());
        when(ratingService.findAllRatings()).thenReturn(ratings);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/list"))
                .andExpect(MockMvcResultMatchers.model().attribute("ratings", ratings));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testAddRatingForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/add"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testValidate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "Aaa")
                        .param("fitchRating", "Aaa")
                        .param("orderNumber", "1")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testShowUpdateForm() throws Exception {
        when(ratingService.findRatingById(1)).thenReturn(Optional.of(new Rating()));

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/update"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testUpdateRating() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "Aaa")
                        .param("fitchRating", "Aaa")
                        .param("orderNumber", "1")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testDeleteRating() throws Exception {
        doNothing().when(ratingService).deleteRating(1);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
    }
}
