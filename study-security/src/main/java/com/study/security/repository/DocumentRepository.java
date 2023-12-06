package com.study.security.repository;

import com.study.security.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT d FROM Document d WHERE d.beneficiary.id = :beneficiaryId")
    List<Document> listAllDocumentsByBeneficiaryId(@Param("beneficiaryId") Long id);
}
