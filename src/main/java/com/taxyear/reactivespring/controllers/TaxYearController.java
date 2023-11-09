package com.taxyear.reactivespring.controllers;

import com.taxyear.reactivespring.entities.TaxInformation;
import com.taxyear.reactivespring.repository.TaxYearRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping()
public class TaxYearController {
    private final TaxYearRepository repository;

    public TaxYearController(TaxYearRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/tax-information-file")
    public Mono<ResponseEntity<TaxInformation>> getTaxYear(@RequestParam int year) {
        return repository.findByYear(year)
            .map(value ->
                ResponseEntity.ok()
                    .contentType(APPLICATION_JSON)
                    .body(value))

            .switchIfEmpty(Mono.error(new IllegalArgumentException()));
    }

    @GetMapping("/tax-information-files")
    public Flux<TaxInformation> getAllTaxYear() {
        return repository.findAll();
    }
}
