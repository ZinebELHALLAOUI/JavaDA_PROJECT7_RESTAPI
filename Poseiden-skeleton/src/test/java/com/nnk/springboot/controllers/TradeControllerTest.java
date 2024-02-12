package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Test
    @WithMockUser(authorities = "USER")
    public void testHome() throws Exception {
        List<Trade> trades = Arrays.asList(new Trade(), new Trade());
        when(tradeService.findAllTrades()).thenReturn(trades);

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/list"))
                .andExpect(MockMvcResultMatchers.model().attribute("trades", trades));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testAddUserForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/add"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testValidate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("buyQuantity", "10.0")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testShowUpdateForm() throws Exception {
        when(tradeService.findTradeById(1)).thenReturn(Optional.of(new Trade()));

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/update"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testUpdateTrade() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/1")
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("buyQuantity", "10.0")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testDeleteTrade() throws Exception {
        doNothing().when(tradeService).deleteTrade(1);

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
    }
}
