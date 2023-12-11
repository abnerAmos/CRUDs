package com.study.security.dto.request;

import jakarta.validation.constraints.NotBlank;

import static com.study.security.dto.request.BeneficiaryRequest.NOT_BLANK;

public record DocumentRequest(
        @NotBlank(message = NOT_BLANK)
        String documentType,
        String description) {
}
