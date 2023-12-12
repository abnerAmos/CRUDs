package com.study.security.service.beneficiary;

import com.study.security.dto.request.BeneficiaryRequest;
import com.study.security.dto.response.BeneficiaryResponse;
import com.study.security.dto.response.DocumentResponse;
import com.study.security.exceptions.BeneficiaryNotFoundException;
import com.study.security.exceptions.BeneficiaryNotSaveException;
import com.study.security.model.Beneficiary;
import com.study.security.model.Document;
import com.study.security.repository.BeneficiaryRepository;
import com.study.security.repository.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            beneficiary.setActive(true);
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

            log.info("Beneficiário registrado com sucesso!");
            return responseCreate(beneficiary, documents);
        } catch (BeneficiaryNotSaveException e) {
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

    @Transactional
    @Override
    public Beneficiary updateBeneficiary(Long id, BeneficiaryRequest request) {
        try {
            Beneficiary beneficiary = beneficiaryRepository.findById(id)
                    .orElseThrow(() -> new BeneficiaryNotFoundException("Beneficiário nao encontrado com o id: " + id));

            if (request.name() != null)
                beneficiary.setName(request.name());
            if (request.phone() != null)
                beneficiary.setPhone(request.phone());
            if (request.birthday() != null)
                beneficiary.setBirthday(formatDate(request.birthday()));
            beneficiary.setUpdateDate(LocalDate.now());

            log.info("Beneficiário atualizado com sucesso!");
            return beneficiary;
        } catch (BeneficiaryNotSaveException e) {
            throw new BeneficiaryNotSaveException("Erro ao processar solicitação.");
        }
    }

    @Transactional
    @Override
    public void deleteBeneficiary(Long id) {
        Beneficiary beneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new BeneficiaryNotFoundException("Beneficiário nao encontrado com o id: " + id));

        beneficiary.setActive(false);
//        função utilizada para exclusão definitiva no banco de dados
//        beneficiaryRepository.delete(beneficiary);
        log.info("Beneficiário excluido com sucesso!");
    }

    @Override
    public Beneficiary getById(Long id) {
        return beneficiaryRepository.findById(id)
                    .orElseThrow(() -> new BeneficiaryNotFoundException("Beneficiário nao encontrado com o id: " + id));
    }

    @Override
    public Page<Beneficiary> findAll(Pageable pageable) {
        Page<Beneficiary> beneficiaries = beneficiaryRepository.findAllByActiveTrue(pageable);
        if (beneficiaries.isEmpty()) {
            throw new BeneficiaryNotFoundException("Não há beneficiários registrados.");
        }

        return beneficiaries;
    }
}
