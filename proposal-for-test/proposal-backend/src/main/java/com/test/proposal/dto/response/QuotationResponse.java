package com.test.proposal.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class QuotationResponse {

    private String fromCurrencyCode;
    private String toCurrencyCode;
    private BigDecimal cotacao;
    private String dataHoraCotacao;
    private BigDecimal exchangeValue;
    private String exchangeCurrencyDescription;

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public void setFromCurrencyCode(String fromCurrencyCode) {
        this.fromCurrencyCode = fromCurrencyCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public void setToCurrencyCode(String toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
    }

    public BigDecimal getCotacao() {
        return cotacao;
    }

    public void setCotacao(BigDecimal cotacao) {
        this.cotacao = cotacao;
    }

    public String getDataHoraCotacao() {
        return dataHoraCotacao;
    }

    public void setDataHoraCotacao(String dataHoraCotacao) {
        this.dataHoraCotacao = dataHoraCotacao;
    }

    public BigDecimal getExchangeValue() {
        return exchangeValue;
    }

    public void setExchangeValue(BigDecimal exchangeValue) {
        this.exchangeValue = exchangeValue;
    }

    public String getExchangeCurrencyDescription() {
        return exchangeCurrencyDescription;
    }

    public void setExchangeCurrencyDescription(String exchangeCurrencyDescription) {
        this.exchangeCurrencyDescription = exchangeCurrencyDescription;
    }
}
