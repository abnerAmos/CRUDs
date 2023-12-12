package com.study.security.service.document;

import com.study.security.exceptions.DocumentNotFoundException;
import com.study.security.model.Document;
import com.study.security.repository.DocumentRepository;
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
            throw new DocumentNotFoundException("Não há documentos vinculados ao beneficiário com id: " + id);
        }

        return documents;
    }
}
