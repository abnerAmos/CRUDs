package com.study.security.service;

import com.study.security.model.Document;

import java.util.List;

public interface DocumentService {

    List<Document> getDocumentsByBeneficiary(Long id);
}
