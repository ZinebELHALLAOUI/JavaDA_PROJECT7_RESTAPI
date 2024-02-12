package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @Test
    @WithMockUser(authorities = "USER")
    public void testHome() throws Exception {
        List<RuleName> ruleNames = Arrays.asList(new RuleName(), new RuleName());
        when(ruleNameService.findAllRuleNames()).thenReturn(ruleNames);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ruleName/list"))
                .andExpect(MockMvcResultMatchers.model().attribute("ruleNames", ruleNames));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testAddRuleForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testValidate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                        .param("name", "RuleName")
                        .param("description", "Description")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SqlStr")
                        .param("sqlPart", "SqlPart")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testShowUpdateForm() throws Exception {
        when(ruleNameService.findRuleNameById(1)).thenReturn(Optional.of(new RuleName()));

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ruleName/update"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testUpdateRuleName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
                        .param("name", "RuleName")
                        .param("description", "Description")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SqlStr")
                        .param("sqlPart", "SqlPart")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testDeleteRuleName() throws Exception {
        doNothing().when(ruleNameService).deleteRuleName(1);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));
    }
}
