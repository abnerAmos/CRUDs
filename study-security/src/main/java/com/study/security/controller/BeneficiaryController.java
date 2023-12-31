package com.study.security.controller;

import com.study.security.dto.request.BeneficiaryRequest;
import com.study.security.dto.response.BeneficiaryResponse;
import com.study.security.model.Beneficiary;
import com.study.security.model.Document;
import com.study.security.service.beneficiary.BeneficiaryService;
import com.study.security.service.document.DocumentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/beneficiary")
public class BeneficiaryController {

    @Autowired
    BeneficiaryService beneficiaryService;

    @Autowired
    DocumentService documentService;

    @SecurityRequirement(name = "bearer-key")
    @PostMapping("/register")
    public ResponseEntity<BeneficiaryResponse> register(@RequestBody @Valid BeneficiaryRequest request, UriComponentsBuilder uriBuilder) {
        BeneficiaryResponse beneficiary = beneficiaryService.registerBeneficiary(request);
        URI uri = uriBuilder.path("/beneficiary/{id}").buildAndExpand(beneficiary.id()).toUri();
        return ResponseEntity.created(uri).body(beneficiary);
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/{id}")
    public ResponseEntity<Beneficiary> getBeneficiary(@PathVariable Long id) {
        return ResponseEntity.ok().body(beneficiaryService.getById(id));
    }

    /* Para paginar basta apenas passar no fim da url... ?size=1&page=1;
    * Caso não for informado retornará a quantidade padrão de itens: 20;
    * É possivel ordenar através do parâmetro sort e atributo... ?sort=name;
    * Com @PageableDefault é possivel alterar o retorno padrão, caso não informado page, size ou sort. */

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/list-beneficiary")
    public ResponseEntity<Page<Beneficiary>> listBeneficiary(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok().body(beneficiaryService.findAll(pageable));
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/list-documents/{id}")
    public ResponseEntity<List<Document>> listDocuments(@PathVariable Long id) {
        return ResponseEntity.ok().body(documentService.getDocumentsByBeneficiary(id));
    }

    @SecurityRequirement(name = "bearer-key")
    @PatchMapping("/update-beneficiary/{id}")
    public ResponseEntity<Beneficiary> updateBeneficiary(@PathVariable Long id,
                                                         @RequestBody BeneficiaryRequest request) {
        Beneficiary response = beneficiaryService.updateBeneficiary(id, request);
        return ResponseEntity.ok().body(response);
    }

    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBeneficiary(@PathVariable Long id) {
        beneficiaryService.deleteBeneficiary(id);
        return ResponseEntity.noContent().build();
    }
}
