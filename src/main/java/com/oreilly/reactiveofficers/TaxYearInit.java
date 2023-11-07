package com.oreilly.reactiveofficers;

import com.oreilly.reactiveofficers.entities.TaxInformation;
import com.oreilly.reactiveofficers.repository.TaxYearRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
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
