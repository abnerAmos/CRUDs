package com.study.security.exceptions;

public class BeneficiaryNotFoundException extends RuntimeException {

    public BeneficiaryNotFoundException(String message) {
        super(message);
    }
}
