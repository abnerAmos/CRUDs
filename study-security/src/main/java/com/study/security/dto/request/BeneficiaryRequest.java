package com.study.security.dto.request;

import jakarta.validation.constraints.Size;

import java.util.List;

public record BeneficiaryRequest(
        String name,
        @Size(min = 8, max = 11, message = "O contato deve possuir de 8 a 11 caracteres")
        String phone,
        String birthday,
        List<DocumentRequest> documents
) {
}
