package com.study.security.service.impl;

import com.study.security.dto.request.BeneficiaryRequest;
import com.study.security.dto.response.BeneficiaryResponse;
import com.study.security.dto.response.DocumentResponse;
import com.study.security.exceptions.BeneficiaryNotFoundException;
import com.study.security.exceptions.BeneficiaryNotSaveException;
import com.study.security.model.Beneficiary;
import com.study.security.model.Document;
import com.study.security.repository.BeneficiaryRepository;
import com.study.security.repository.DocumentRepository;
import com.study.security.service.BeneficiaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.study.security.util.DateUtil.formatDate;

@Service
@Slf4j
public class BeneficiaryImpl implements BeneficiaryService {

    @Autowired
    BeneficiaryRepository beneficiaryRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Transactional
    @Override
    public BeneficiaryResponse registerBeneficiary(BeneficiaryRequest beneficiaryRequest) {
        try {
            Beneficiary beneficiary = new Beneficiary();
            beneficiary.setName(beneficiaryRequest.name());
            beneficiary.setPhone(beneficiaryRequest.phone());
            beneficiary.setBirthday(formatDate(beneficiaryRequest.birthday()));
            beneficiary.setInclusionDate(LocalDate.now());
            beneficiaryRepository.save(beneficiary);

            List<Document> documents =  beneficiaryRequest.documents().stream()
                    .map(request -> {
                        Document document = new Document();
                        document.setDocumentType(request.documentType());
                        document.setDescription(request.description());
                        document.setInclusionDate(LocalDate.now());
                        document.setBeneficiary(beneficiary);
                        return document;
                    }).toList();

            documentRepository.saveAll(documents);
            return responseCreate(beneficiary, documents);
        } catch (BeneficiaryNotSaveException e) {
            log.error("Erro ao registrar beneficiário: {}", e.getMessage());
            throw new BeneficiaryNotSaveException("Erro ao registrar beneficiário.");
        }
    }

    public BeneficiaryResponse responseCreate(Beneficiary beneficiary, List<Document> document) throws BeneficiaryNotSaveException {
        return new BeneficiaryResponse(
                beneficiary.getId(),
                beneficiary.getName(),
                beneficiary.getPhone(),
                formatDate(beneficiary.getBirthday()),
                formatDate(beneficiary.getInclusionDate()),
                documents(document)
        );
    }

    private List<DocumentResponse> documents(List<Document> documents) {
        return documents.stream()
                .map(doc -> new DocumentResponse(
                        doc.getId(),
                        doc.getDocumentType(),
                        doc.getDescription(),
                        formatDate(doc.getInclusionDate())
                )).toList();
    }

    @Override
    public Beneficiary updateBeneficiary(Long id, BeneficiaryRequest request) {
        try {
            Beneficiary beneficiary = beneficiaryRepository.findById(id)
                    .orElseThrow(() -> new BeneficiaryNotFoundException("Beneficiário nao encontrado."));

            beneficiary.setName(request.name());
            beneficiary.setPhone(request.phone());
            beneficiary.setBirthday(formatDate(request.birthday()));
            beneficiary.setUpdateDate(LocalDate.now());

            beneficiaryRepository.save(beneficiary);
            return beneficiary;
        } catch (BeneficiaryNotSaveException e) {
            log.error("Erro ao registrar beneficiário: {}", e.getMessage());
            throw new BeneficiaryNotSaveException("Erro ao processar solicitação.");
        } catch (BeneficiaryNotFoundException e) {
            log.error("Erro ao encontrar beneficiário: {}", e.getMessage());
            throw new BeneficiaryNotFoundException("Beneficiário nao encontrado.");
        }
    }

    @Transactional
    @Override
    public void deleteBeneficiary(Long id) {
        try {
            Beneficiary beneficiary = beneficiaryRepository.findById(id)
                    .orElseThrow(() -> new BeneficiaryNotFoundException("Beneficiário nao encontrado com o id: " + id));

            beneficiaryRepository.delete(beneficiary);
        } catch (BeneficiaryNotFoundException e) {
            log.error("Erro ao excluir: {}", e.getMessage());
            throw new BeneficiaryNotFoundException("Não foi possivel excluir beneficiário.");
        }
    }

    @Override
    public Beneficiary getById(Long id) {
        try {
            return beneficiaryRepository.findById(id)
                    .orElseThrow(() -> new BeneficiaryNotFoundException("Beneficiário nao encontrado com o id: " + id));
        } catch (BeneficiaryNotFoundException e) {
            log.error(e.getMessage());
            throw new BeneficiaryNotFoundException("Beneficiário nao encontrado.");
        }
    }

    @Override
    public List<Beneficiary> findAll() {
        List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
        if (beneficiaries.isEmpty()) {
            log.error("Não há beneficiários registrados.");
            throw new BeneficiaryNotFoundException("Não há beneficiários registrados.");
        }

        return beneficiaries;
    }
}
