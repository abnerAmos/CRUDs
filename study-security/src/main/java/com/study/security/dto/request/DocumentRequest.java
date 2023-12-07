package com.study.security.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DocumentRequest(
        @NotBlank
        String documentType,
        String description) {
}
