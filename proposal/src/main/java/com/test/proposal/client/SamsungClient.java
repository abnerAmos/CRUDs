package com.test.proposal.client;

import com.test.proposal.dto.response.CurrencyResponse;
import com.test.proposal.dto.response.DocumentsResponse;
import com.test.proposal.dto.response.QuotationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Component
@FeignClient(name = "quotation-api", url = "https://sdshealthcheck.cellologistics.com.br/sds-devs-evaluation/evaluation")
public interface SamsungClient {

    @GetMapping("/quotation")
    Collection<QuotationResponse> getQuotation();

    @GetMapping("/currency")
    Collection<CurrencyResponse> getCurrency();

    @GetMapping("/docs")
    Collection<DocumentsResponse> getDocuments();
}
