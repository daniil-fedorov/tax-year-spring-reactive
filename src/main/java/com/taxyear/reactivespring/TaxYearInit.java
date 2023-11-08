package com.taxyear.reactivespring;

import com.taxyear.reactivespring.entities.TaxInformation;
import com.taxyear.reactivespring.repository.TaxYearRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class TaxYearInit implements ApplicationRunner {
    private final TaxYearRepository taxYearRepository;

    public TaxYearInit(TaxYearRepository taxYearRepository) {
        this.taxYearRepository = taxYearRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        taxYearRepository.deleteAll()
           .thenMany(Flux.just(TaxInformation.builder().year(2022).standardPersonalAllowance(201231).build()))
           .flatMap(taxYearRepository::save)
           .thenMany(taxYearRepository.findAll())
           .subscribe(System.out::println);
    }
}
