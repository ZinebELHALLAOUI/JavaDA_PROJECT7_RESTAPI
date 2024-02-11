package com.nnk.springboot.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration de la sécurité de l'application.
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    /**
     * Bean pour l'encodeur de mot de passe.
     *
     * @return L'encodeur de mot de passe.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration de la chaîne de filtres de sécurité.
     *
     * @param http La configuration HTTP de la sécurité.
     * @return La chaîne de filtres de sécurité configurée.
     * @throws Exception si une exception survient lors de la configuration de la sécurité.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/", "/login", "/static/**", "/css/**").permitAll() // Autoriser l'accès à certaines URL sans authentification
                                .requestMatchers("/bidList/**", "/rating/**", "/ruleName/**", "/trade/**", "/curvePoint/**", "/app/error", "/app/logout").hasAnyAuthority("ADMIN", "USER") // Autoriser l'accès à certaines URL pour les utilisateurs avec les rôles "ADMIN" ou "USER"
                                .requestMatchers("/user/**", "/admin/**").hasAuthority("ADMIN") // Autoriser l'accès à certaines URL uniquement pour les utilisateurs avec le rôle "ADMIN"
                                .anyRequest().authenticated()) // Toutes les autres requêtes nécessitent une authentification
                .formLogin(Customizer.withDefaults()) // Configuration par défaut pour la gestion de la connexion via un formulaire
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/app/error")); // Gestion des exceptions liées aux autorisations refusées
        return http.build();
    }
}
