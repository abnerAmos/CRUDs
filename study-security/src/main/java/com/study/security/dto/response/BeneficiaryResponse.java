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
//    public BeneficiaryResponse(Beneficiary beneficiary) {
//        this(
//                beneficiary.getId(),
//                beneficiary.getName(),
//                beneficiary.getPhone(),
//                formatDate(beneficiary.getBirthday()),
//                formatDate(beneficiary.getInclusionDate()),
//                beneficiary.getDocument());
//    }
    /* Gerando um construtor e passando como parâmetro o próprio objeto podemos
    * retornar a lista com um stream e map.
    *
    * Exemplo: beneficiaryRepository.findAll().stream.map(BeneficiaryResponse::new);
    * record começa a ficar responsável também por construir o objeto de resposta*/
}
