package com.oreilly.reactiveofficers.controllers;

import com.oreilly.reactiveofficers.repository.TaxYearRepository;
import com.oreilly.reactiveofficers.entities.TaxInformation;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tax-information-file")
public class TaxYearController {
    private TaxYearRepository repository;

    public TaxYearController(TaxYearRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/tax-information-file")
    public Mono<TaxInformation> getTaxYear(@RequestParam int year) {
        return repository.findByYear(year);
    }
}
