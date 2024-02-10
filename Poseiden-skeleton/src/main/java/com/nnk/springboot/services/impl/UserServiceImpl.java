package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.Assert;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Creating user : " + user);
        Assert.isNotFound(userRepository.existsByUsername(user.getUsername()), "Username already exists");
        return userRepository.save(user);
    }

    @Override
    public User updateUser(int id, User user) {
        log.info("Updating user : " + user);
        var userFound = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user does not exist"));
        var userByUserNameFound = userRepository.findByUsername(user.getUsername());
        if (userByUserNameFound.isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }
        userFound.setPassword(passwordEncoder.encode(user.getPassword()));
        userFound.setUsername(user.getUsername());
        userFound.setFullname(user.getFullname());
        userFound.setRole(user.getRole());
        return userRepository.saveAndFlush(userFound);
    }

    @Override
    public List<User> findAll() {
        log.info("Find all users");
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(int id) {
        log.info("Find user by id : " + id);
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        log.info("Deleting by user id : " + id);
        Assert.isFound(userRepository.existsById(id), "user requested for delete does not exist");
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
