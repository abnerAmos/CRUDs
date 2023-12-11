package com.study.security.repository;

import com.study.security.model.Beneficiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

    Page<Beneficiary> findAllByActiveTrue(Pageable pageable);
}
