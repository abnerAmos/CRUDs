package com.study.security.config;

import com.study.security.exceptions.BeneficiaryNotFoundException;
import com.study.security.exceptions.BeneficiaryNotSaveException;
import com.study.security.exceptions.DocumentNotFoundException;
import com.study.security.exceptions.TokenNullException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler(TokenNullException.class)
    @ResponseBody public ResponseEntity<StandartError> tokenNullException(TokenNullException e, HttpServletRequest request) {
        String error = "Header Authorization não possui token.";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandartError err = new StandartError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        log.error("ERRO :: " + e.getMessage());
        return ResponseEntity.status(status).body(err);
    }
}
