package com.oreilly.reactiveofficers.controllers;

import com.oreilly.reactiveofficers.repository.TaxYearRepository;
import com.oreilly.reactiveofficers.entities.TaxInformation;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping()
public class TaxYearController {
    private final TaxYearRepository repository;

    public TaxYearController(TaxYearRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/tax-information-file")
    public Mono<TaxInformation> getTaxYear(@RequestParam int year) {
        return repository.findByYear(year);
    }

    @GetMapping("/tax-information-files")
    public Flux<TaxInformation> getAllTaxYear() {
        return repository.findAll();
    }
}
