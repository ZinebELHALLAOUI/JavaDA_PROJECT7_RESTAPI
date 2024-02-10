package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private final UserService userService = new UserServiceImpl(userRepository, passwordEncoder);

    @Test
    public void testCreateUser() {
        // Mocking the repository
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setFullname("Test User");
        user.setRole(Role.USER);

        when(passwordEncoder.encode(user.getPassword())).thenReturn("hashedPassword");
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        // Calling service method
        User result = userService.createUser(user);

        // Verifying the result
        assertEquals("hashedPassword", result.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    public void testUpdateUser() {
        // Mocking the repository
        int userId = 1;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("existingUser");
        existingUser.setPassword("existingPassword");
        existingUser.setFullname("Existing User");
        existingUser.setRole(Role.USER);

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setUsername("updatedUser");
        updatedUser.setPassword("updatedPassword");
        updatedUser.setFullname("Updated User");
        updatedUser.setRole(Role.ADMIN);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername(updatedUser.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("hashedPassword");
        when(userRepository.saveAndFlush(existingUser)).thenReturn(updatedUser);

        // Calling service method
        User result = userService.updateUser(userId, updatedUser);

        // Verifying the result
        assertEquals(updatedUser.getUsername(), result.getUsername());
        assertEquals(updatedUser.getFullname(), result.getFullname());
        assertEquals(updatedUser.getRole(), result.getRole());
        verify(userRepository).saveAndFlush(existingUser);
    }

    @Test
    public void testFindAll() {
        // Mocking the repository
        List<User> expectedUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Calling service method
        List<User> result = userService.findAll();

        // Verifying the result
        assertEquals(expectedUsers, result);
    }

    @Test
    public void testFindById() {
        // Mocking the repository
        int userId = 1;
        User expectedUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // Calling service method
        Optional<User> result = userService.findById(userId);

        // Verifying the result
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
    }

    @Test
    public void testDeleteById() {
        // Mocking the repository
        int userId = 1;
        when(userRepository.existsById(userId)).thenReturn(true);

        // Calling service method
        userService.deleteById(userId);

        // Verifying the delete operation
        Mockito.verify(userRepository).deleteById(userId);
    }

    @Test
    public void testLoadUserByUsername() {
        // Mocking the repository
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        user.setPassword("hashedPassword");
        user.setRole(Role.USER);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Calling service method
        var userService = (UserDetailsService) this.userService;
        UserDetails userDetails = userService.loadUserByUsername(username);

        // Verifying the result
        assertEquals(username, userDetails.getUsername());
        assertEquals("hashedPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Role.USER.name())));
        assertFalse(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Role.ADMIN.name())));
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Mocking the repository
        String username = "nonExistingUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Verifying the exception
        var userService = (UserDetailsService) this.userService;
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }
}
