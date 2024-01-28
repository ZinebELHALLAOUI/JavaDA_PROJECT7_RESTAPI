package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotEmpty(message = "Le mot de passe ne peut pas être vide")
    @Size(min = 8, message = "Le mot de passe doit avoir au moins 8 caractères")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%^&*()-_+=]).*$",
            message = "Le mot de passe doit contenir au moins une lettre majuscule, un chiffre et un symbole"
    )
    private String password;

    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @Enumerated(EnumType.STRING)
    private Role role;
}
