package com.taxyear.reactivespring;

import com.taxyear.reactivespring.entities.TaxInformation;
import com.taxyear.reactivespring.repository.TaxYearRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
public class TaxYearInit implements ApplicationRunner {
    private final TaxYearRepository taxYearRepository;

    private final List<TaxInformation> taxInformationList = Arrays.asList(
        TaxInformation.builder().year(2022).standardPersonalAllowance(2202).build(),
        TaxInformation.builder().year(2023).standardPersonalAllowance(3202).build()
    );

    public TaxYearInit(TaxYearRepository taxYearRepository) {
        this.taxYearRepository = taxYearRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        taxYearRepository.deleteAll()
            .thenMany(Flux.fromIterable(taxInformationList))
            .flatMap(taxYearRepository::save)
            .thenMany(taxYearRepository.findAll())
            .subscribe(System.out::println);
    }
}
