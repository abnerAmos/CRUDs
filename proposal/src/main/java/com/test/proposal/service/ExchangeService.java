package com.test.proposal.service;

import com.test.proposal.client.SamsungClient;
import com.test.proposal.dto.response.CurrencyResponse;
import com.test.proposal.dto.response.DocumentsResponse;
import com.test.proposal.dto.response.ExchangeResponse;
import com.test.proposal.dto.response.QuotationResponse;
import com.test.proposal.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.test.proposal.util.DateUtil.stringToLocalDate;

@Service
public class ExchangeService {

    @Autowired
    private final SamsungClient samsungClient;

    public ExchangeService(SamsungClient samsungClient) {
        this.samsungClient = samsungClient;
    }

    public List<ExchangeResponse> getExchange(String date, String currencyCode) {
        return findExchangeResult(date, currencyCode);
    }

    private List<ExchangeResponse> findExchangeResult(String date, String currencyCode) {

        var listDocs = findDocs(date, currencyCode);
        var quotations = findQuotations(date, currencyCode);

        List<ExchangeResponse> listExchange = new ArrayList<>();

        for (DocumentsResponse doc : listDocs) {
            ExchangeResponse exchange = new ExchangeResponse();
            exchange.setDocumentNumber(doc.getDocumentNumber());
            exchange.setInvoice(doc.getNotaFiscal());
            exchange.setDocumentDate(stringToLocalDate(doc.getDocumentDate()));
            exchange.setCurrencyCode(currencyCode);
            exchange.setCurrencyDescription(getDescription(currencyCode));
            exchange.setDocumentValue(doc.getDocumentValue());

            List<QuotationResponse> listQuotations = new ArrayList<>();

            for (QuotationResponse quotation : quotations) {
                QuotationResponse currencyQuotation = new QuotationResponse();
                currencyQuotation.setFromCurrencyCode(quotation.getFromCurrencyCode());
                currencyQuotation.setToCurrencyCode(quotation.getToCurrencyCode());
                currencyQuotation.setCotacao(quotation.getCotacao());
                currencyQuotation.setDataHoraCotacao(quotation.getDataHoraCotacao());
                currencyQuotation.setExchangeValue(calculate(doc, quotation));
                currencyQuotation.setExchangeCurrencyDescription(getDescription(quotation.getToCurrencyCode()));

                listQuotations.add(currencyQuotation);
            }
            exchange.setQuotations(listQuotations);
            listExchange.add(exchange);
        }
        return listExchange;
    }

    private List<DocumentsResponse> findDocs(String date, String currencyCode) {

        var docs = samsungClient.getDocuments().stream()
                .filter(e -> e.getDocumentDate().equals(date))
                .filter(e -> e.getCurrencyCode().equals(currencyCode))
                .collect(Collectors.toList());

        if (docs.isEmpty())
            return getLastDocuments(currencyCode);

        return docs;
    }

    private List<QuotationResponse> findQuotations(String date, String currencyCode) {

        var quotations = samsungClient.getQuotation().stream()
                .filter(e -> e.getDataHoraCotacao().equals(date))
                .filter(e -> e.getFromCurrencyCode().equals(currencyCode))
                .map(e -> {
                    QuotationResponse formattedQuotation = new QuotationResponse();
                    formattedQuotation.setFromCurrencyCode(e.getFromCurrencyCode());
                    formattedQuotation.setToCurrencyCode(e.getToCurrencyCode());
                    formattedQuotation.setCotacao(e.getCotacao());
                    formattedQuotation.setDataHoraCotacao(stringToLocalDate(e.getDataHoraCotacao()));
                    // Outros atributos que você precisar copiar
                    return formattedQuotation;
                })
                .toList();

        if (quotations.isEmpty()) {
            return getLastQuotation(currencyCode);
        }

        return quotations;
    }

    private List<QuotationResponse> getLastQuotation(String currencyCode) {
        Collection<QuotationResponse> listQuotations = samsungClient.getQuotation();

        return listQuotations.stream()
                .filter(q -> q.getFromCurrencyCode().equals(currencyCode))
                .collect(Collectors.groupingBy(QuotationResponse::getDataHoraCotacao, Collectors.toList()))
                .values()
                .stream()
                .max(Comparator.comparing(list -> list.get(0).getDataHoraCotacao()))
                .orElse(Collections.emptyList());
    }

    private List<DocumentsResponse> getLastDocuments(String currencyCode) {
        Collection<DocumentsResponse> listDocuments = samsungClient.getDocuments();

        return listDocuments.stream()
                .filter(q -> q.getCurrencyCode().equals(currencyCode))
                .collect(Collectors.groupingBy(DocumentsResponse::getDocumentDate, Collectors.toList()))
                .values()
                .stream()
                .max(Comparator.comparing(list -> list.get(0).getDocumentDate()))
                .orElse(Collections.emptyList());
    }

    private String getDescription(String toCurrencyCode) {

        var toCurrency = samsungClient.getCurrency().stream()
                .filter(e -> e.getCurrencyCode().equals(toCurrencyCode))
                .findFirst().orElseThrow(() -> new NotFoundException("Moeda corrente informada não encontrada."));

        return toCurrency.getCurrencyDesc();
    }

    private BigDecimal calculate(DocumentsResponse documentValue, QuotationResponse quotationValue) {
        return documentValue.getDocumentValue()
                .multiply(new BigDecimal(String.valueOf(quotationValue.getCotacao())))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public Set<String> getDate() {
        return samsungClient.getDocuments().stream()
                .map(doc -> stringToLocalDate(doc.getDocumentDate()))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public List<String> getCode() {
        return samsungClient.getCurrency().stream()
                .map(CurrencyResponse::getCurrencyCode)
                .collect(Collectors.toList());
    }
}
