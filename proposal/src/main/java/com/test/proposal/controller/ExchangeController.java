package com.test.proposal.controller;

import com.test.proposal.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/samsung")
@CrossOrigin(origins = "http://localhost:4200/")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @GetMapping("/document")
    public ResponseEntity<?> document(@RequestParam String date,
                                       @RequestParam String currencyCode) {
        var response = exchangeService.getExchange(date, currencyCode);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<?> date() {
        var response = exchangeService.getDate();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/code")
    public ResponseEntity<?> currencyCode() {
        var response = exchangeService.getCode();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
