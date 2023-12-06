package com.study.security.service.impl;

import com.study.security.exceptions.DocumentNotFoundException;
import com.study.security.model.Document;
import com.study.security.repository.DocumentRepository;
import com.study.security.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DocumentImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @Override
    public List<Document> getDocumentsByBeneficiary(Long id) {
        List<Document> documents = documentRepository.listAllDocumentsByBeneficiaryId(id);
        if (documents.isEmpty()) {
            log.error("Beneficiário não possui documentos vinculados com id: " + id);
            throw new DocumentNotFoundException("Não há documentos vinculados ao beneficiário.");
        }

        return documents;
    }
}
