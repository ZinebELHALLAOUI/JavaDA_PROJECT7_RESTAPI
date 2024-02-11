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

/**
 * Implémentation du service gérant les opérations liées aux utilisateurs (User).
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Crée un nouvel utilisateur.
     *
     * @param user L'utilisateur à créer.
     * @return L'utilisateur créé.
     * @throws IllegalArgumentException si le nom d'utilisateur existe déjà.
     */
    @Override
    public User createUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Creating user : " + user);
        Assert.isNotFound(userRepository.existsByUsername(user.getUsername()), "Username already exists");
        return userRepository.save(user);
    }

    /**
     * Met à jour un utilisateur existant.
     *
     * @param id L'identifiant de l'utilisateur à mettre à jour.
     * @param user L'utilisateur mis à jour.
     * @return L'utilisateur mis à jour.
     * @throws NotFoundException si l'utilisateur n'est pas trouvé.
     * @throws IllegalArgumentException si le nom d'utilisateur existe déjà.
     */
    @Override
    public User updateUser(int id, User user) {
        log.info("Updating user : " + user);
        var userFound = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user does not exist"));
        if (!user.getUsername().equalsIgnoreCase(userFound.getUsername()) && userRepository.findByUsername(user.getUsername()).isPresent())
            throw new IllegalArgumentException("Username already exists");
        userFound.setPassword(passwordEncoder.encode(user.getPassword()));
        userFound.setUsername(user.getUsername());
        userFound.setFullname(user.getFullname());
        userFound.setRole(user.getRole());
        return userRepository.saveAndFlush(userFound);
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     *
     * @return La liste de tous les utilisateurs.
     */
    @Override
    public List<User> findAll() {
        log.info("Find all users");
        return userRepository.findAll();
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id L'identifiant de l'utilisateur à récupérer.
     * @return Une option contenant l'utilisateur correspondant s'il existe.
     */
    @Override
    public Optional<User> findById(int id) {
        log.info("Find user by id : " + id);
        return userRepository.findById(id);
    }

    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id L'identifiant de l'utilisateur à supprimer.
     * @throws NotFoundException si l'utilisateur n'est pas trouvé.
     */
    @Override
    public void deleteById(int id) {
        log.info("Deleting by user id : " + id);
        Assert.isFound(userRepository.existsById(id), "user requested for delete does not exist");
        userRepository.deleteById(id);
    }

    /**
     * Charge un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur de l'utilisateur à charger.
     * @return Les détails de l'utilisateur chargé.
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
