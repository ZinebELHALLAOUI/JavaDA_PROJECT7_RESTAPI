package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testHome() throws Exception {
        List<User> users = Arrays.asList(new User(), new User());
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/list"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", users));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testAddUserForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/add"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testValidate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .param("username", "testUser")
                        .param("password", "A@a12345")
                        .param("fullname", "Test User")
                        .param("role", "USER")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testShowUpdateForm() throws Exception {
        User user = new User();
        user.setId(1);
        when(userService.findById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/update"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testUpdateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/1")
                        .param("username", "testUser")
                        .param("password", "A@a12345")
                        .param("fullname", "Test User")
                        .param("role", "USER")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
    }
}
