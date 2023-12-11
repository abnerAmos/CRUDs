package com.study.security.exceptions;

public class TokenExpiredOrNullException extends RuntimeException {
    public TokenExpiredOrNullException(String message) {
        super(message);
    }
}
