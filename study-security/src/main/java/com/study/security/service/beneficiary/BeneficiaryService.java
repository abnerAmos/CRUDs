package com.study.security.service.beneficiary;

import com.study.security.dto.request.BeneficiaryRequest;
import com.study.security.dto.response.BeneficiaryResponse;
import com.study.security.model.Beneficiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BeneficiaryService {

    BeneficiaryResponse registerBeneficiary(BeneficiaryRequest beneficiaryRequest);

    Beneficiary updateBeneficiary(Long id, BeneficiaryRequest request);

    void deleteBeneficiary(Long id);

    Beneficiary getById(Long id);

    Page<Beneficiary> findAll(Pageable pageable);
}
