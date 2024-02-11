package com.nnk.springboot.exceptions;

/**
 * Classe utilitaire pour les assertions personnalisées.
 */
public class Assert extends org.springframework.util.Assert {

    /**
     * Vérifie si l'expression est vraie, sinon lance une exception NotFoundException avec le message spécifié.
     *
     * @param expression L'expression à évaluer.
     * @param message    Le message de l'exception NotFoundException en cas d'échec de l'assertion.
     * @throws NotFoundException si l'expression est fausse.
     */
    public static void isFound(boolean expression, String message) {
        if (!expression) {
            throw new NotFoundException(message);
        }
    }

    /**
     * Vérifie si l'expression est fausse, sinon lance une exception IllegalArgumentException avec le message spécifié.
     *
     * @param expression L'expression à évaluer.
     * @param message    Le message de l'exception IllegalArgumentException en cas d'échec de l'assertion.
     * @throws IllegalArgumentException si l'expression est vraie.
     */
    public static void isNotFound(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
