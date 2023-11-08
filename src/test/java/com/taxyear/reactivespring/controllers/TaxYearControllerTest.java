package com.taxyear.reactivespring.controllers;

import com.taxyear.reactivespring.entities.TaxInformation;
import com.taxyear.reactivespring.repository.TaxYearRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaxYearControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private TaxYearRepository repository;

    private final List<TaxInformation> taxInformationList = Arrays.asList(
        TaxInformation.builder().year(2022).standardPersonalAllowance(12570).build(),
        TaxInformation.builder().year(2023).standardPersonalAllowance(12570).build()
    );

    @AfterEach
    public void setUp() {
        repository.deleteAll()
                  .thenMany(Flux.fromIterable(taxInformationList))
                  .flatMap(repository::save)
                  .doOnNext(System.out::println)
                  .blockLast();
    }

    @Test
    void testGetAllTaxYearInformationFiles() {
        client.get().uri("/tax-information-files")
              .accept(MediaType.APPLICATION_JSON_UTF8)
              .exchange()
              .expectStatus().isOk()
              .expectHeader().contentType(MediaType.APPLICATION_JSON)
              .expectBodyList(TaxInformation.class)
              .hasSize(2)
              .consumeWith(System.out::println);
    }

    @Test
    void testGetTaxYearInformationFile() {
        client.get().uri("/tax-information-file?year={year}", taxInformationList.get(0).getYear())
              .exchange()
              .expectStatus().isOk()
              .expectBody(TaxInformation.class)
              .consumeWith(System.out::println);
    }
}