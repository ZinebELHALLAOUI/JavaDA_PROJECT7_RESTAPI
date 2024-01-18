package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.services.exceptions.Assert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(final User user) {
        log.info("Creating user : " + user);
        Assert.isNull(user.getId(), "user id should be null for creation");
        Assert.isNotFound(userRepository.existsByUsername(user.getUsername()),"Username already exists");
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        log.info("Updating user : " + user);
        final Integer id = user.getId();
        Assert.notNull(id, "user id should not be null for update");
        Assert.isFound(userRepository.existsById(id), "user requested for update does not exist");
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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
}
