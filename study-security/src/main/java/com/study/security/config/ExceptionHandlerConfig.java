package com.study.security.config;

import com.study.security.exceptions.BeneficiaryNotFoundException;
import com.study.security.exceptions.BeneficiaryNotSaveException;
import com.study.security.exceptions.DocumentNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler(BeneficiaryNotFoundException.class)
    public ResponseEntity<StandartError> beneficiaryNotFoundException(BeneficiaryNotFoundException e, HttpServletRequest request) {
        String error = "Erro ao encontrar beneficiário.";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandartError err = new StandartError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        log.error("ERRO :: " + e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(BeneficiaryNotSaveException.class)
    public ResponseEntity<StandartError> entityNotSaveException(BeneficiaryNotSaveException e, HttpServletRequest request) {
        String error = "Erro ao processar solicitação.";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandartError err = new StandartError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        log.error("ERRO :: " + e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<StandartError> documentNotFoundException(DocumentNotFoundException e, HttpServletRequest request) {
        String error = "Erro ao encontrar documento.";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandartError err = new StandartError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        log.error("ERRO :: " + e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StandartError> tokenNullException(AuthenticationException e, HttpServletRequest request) {
        String error = "Header Authorization nulo ou não possui token.";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandartError err = new StandartError(Instant.now(), status.value(), status.getReasonPhrase(), error, request.getRequestURI());
        log.error("ERRO :: " + error);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String error = "Campo obrigatório inválido.";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationError err = new ValidationError(Instant.now(), status.value(), error, request.getRequestURI(), e.getFieldErrors());
        log.error("ERRO :: " + e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

}
