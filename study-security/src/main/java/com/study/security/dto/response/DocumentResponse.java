package com.study.security.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DocumentResponse(
    Long id,
    String documentType,
    String description,
    String inclusionDate
) {
}
