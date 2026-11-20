package com.agrismart.inventory.exception;

/** Erreur de règle métier (ex : stock insuffisant). */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
