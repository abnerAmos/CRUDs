package com.study.security.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BeneficiaryResponse(
    Long id,
    String name,
    String phone,
    String birthday,
    String inclusionDate,
    List<DocumentResponse> documents
) {
}
