package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(final User user);

    User updateUser(int id, final User user);

    List<User> findAll();

    Optional<User> findById(int id);

    void deleteById(int id);
}
