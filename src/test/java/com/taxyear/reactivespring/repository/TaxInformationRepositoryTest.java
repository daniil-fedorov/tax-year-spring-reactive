package com.taxyear.reactivespring.repository;

import com.taxyear.reactivespring.entities.TaxInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TaxInformationRepositoryTest {
    @Autowired
    private TaxYearRepository repository;

    private final List<TaxInformation> taxInformationList = Arrays.asList(
        TaxInformation.builder().year(2022).standardPersonalAllowance(12570).build(),
        TaxInformation.builder().year(2023).standardPersonalAllowance(12570).build()
    );


    @BeforeEach
    public void setUp() {
        repository.deleteAll()
                  .thenMany(Flux.fromIterable(taxInformationList))
                  .flatMap(repository::save)
                  .blockLast();
    }

    @Test
    void save() {

        TaxInformation build = TaxInformation.builder().year(2012).standardPersonalAllowance(12570).build();
        StepVerifier.create(repository.save(build))
                    .expectNextMatches(taxInformation -> taxInformation.getYear() == 2012)
                    .verifyComplete();
    }

    @Test
    void findAll() {
        StepVerifier.create(repository.findAll())
                    .expectNextCount(2)
                    .verifyComplete();
    }

    @Test
    void findByYear() {
        taxInformationList.stream()
                .map(TaxInformation::getYear)
                .forEach(year ->
                                 StepVerifier.create(repository.findByYear(year))
                                             .expectNextCount(1)
                                             .verifyComplete());
    }

    @Test
    void findByIdNotExist() {
        StepVerifier.create(repository.findByYear(0))
                    .verifyComplete();
    }

    @Test
    void count() {
        StepVerifier.create(repository.count())
                    .expectNext(2L)
                    .verifyComplete();
    }

}