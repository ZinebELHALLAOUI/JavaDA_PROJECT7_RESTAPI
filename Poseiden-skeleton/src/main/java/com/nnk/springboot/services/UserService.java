package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void createOrUpdateUser(final User user);

    List<User> findAll();

    Optional<User> findById(Integer id);

    void deleteById(int id);
}
