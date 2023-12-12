package com.study.security.service.document;

import com.study.security.model.Document;

import java.util.List;

public interface DocumentService {

    List<Document> getDocumentsByBeneficiary(Long id);
}
