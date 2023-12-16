package com.test.proposal.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DocumentsResponse {

    private Long documentId;
    private Long documentNumber;
    private String notaFiscal;
    private String documentDate;
    private BigDecimal documentValue;
    private String currencyCode;

    public Long getDocumentId() {
        return documentId;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public BigDecimal getDocumentValue() {
        return documentValue;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
