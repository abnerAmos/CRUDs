package com.study.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BeneficiaryRequest(
        /* Na requisição é possivel incluir ACCEPT-LANGUAGE e podemos infomar qual lingua
        de retorno da mensagem padrão do BeanValidation, exemplo: pt-br */
        @NotBlank(message = NOT_BLANK)
        String name,
        @Size(min = 8, max = 11, message = "O contato deve possuir de 8 a 11 caracteres")
        String phone,
        @NotBlank(message = NOT_BLANK)
        String birthday,
        List<DocumentRequest> documents
) {
        public static final String NOT_BLANK = "Campo não pode ser nulo ou vazio.";
}
