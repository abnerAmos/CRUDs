package com.study.security.controller;

import com.study.security.dto.request.BeneficiaryRequest;
import com.study.security.dto.response.BeneficiaryResponse;
import com.study.security.model.Beneficiary;
import com.study.security.model.Document;
import com.study.security.service.BeneficiaryService;
import com.study.security.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/beneficiary")
public class BeneficiaryController {

    @Autowired
    BeneficiaryService beneficiaryService;

    @Autowired
    DocumentService documentService;

    @PostMapping("/register")
    public ResponseEntity<BeneficiaryResponse> register(@RequestBody @Valid BeneficiaryRequest request) {
        BeneficiaryResponse beneficiary = beneficiaryService.registerBeneficiary(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/beneficiary/{id}")
                .buildAndExpand(beneficiary.getId()).toUri();
        return ResponseEntity.created(uri).body(beneficiary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficiary> getBeneficiary(@PathVariable Long id) {
        return ResponseEntity.ok().body(beneficiaryService.getById(id));
    }

    @GetMapping("/list-beneficiary")
    public ResponseEntity<List<Beneficiary>> listBeneficiary () {
        return ResponseEntity.ok().body(beneficiaryService.findAll());
    }

    @GetMapping("/list-documents/{id}")
    public ResponseEntity<List<Document>> listDocuments(@PathVariable Long id) {
        return ResponseEntity.ok().body(documentService.getDocumentsByBeneficiary(id));
    }

    @PatchMapping("/update-beneficiary/{id}")
    public ResponseEntity<BeneficiaryResponse> updateBeneficiary(@PathVariable Long id,
                                                                 @RequestBody BeneficiaryRequest request) {
        BeneficiaryResponse response = beneficiaryService.updateBeneficiary(id, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBeneficiary(@PathVariable Long id) {
        beneficiaryService.deleteBeneficiary(id);
        return ResponseEntity.noContent().build();
    }
}
