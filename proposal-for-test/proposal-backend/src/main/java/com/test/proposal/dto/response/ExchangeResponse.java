package com.test.proposal.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ExchangeResponse {

    private Long documentNumber;
    private String invoice;
    private String documentDate;
    private BigDecimal documentValue;
    private String currencyCode;
    private String currencyDescription;
    private List<QuotationResponse> quotations;

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public BigDecimal getDocumentValue() {
        return documentValue;
    }

    public void setDocumentValue(BigDecimal documentValue) {
        this.documentValue = documentValue;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyDescription() {
        return currencyDescription;
    }

    public void setCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
    }

    public List<QuotationResponse> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<QuotationResponse> quotations) {
        this.quotations = quotations;
    }
}
