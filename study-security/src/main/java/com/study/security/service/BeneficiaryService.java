package com.study.security.service;

import com.study.security.dto.request.BeneficiaryRequest;
import com.study.security.dto.response.BeneficiaryResponse;
import com.study.security.model.Beneficiary;

import java.util.List;

public interface BeneficiaryService {

    BeneficiaryResponse registerBeneficiary(BeneficiaryRequest beneficiaryRequest);

    BeneficiaryResponse updateBeneficiary(Long id, BeneficiaryRequest request);

    void deleteBeneficiary(Long id);

    Beneficiary getById(Long id);

    List<Beneficiary> findAll();
}
