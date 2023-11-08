package com.taxyear.reactivespring.controllers;

import com.taxyear.reactivespring.entities.TaxInformation;
import com.taxyear.reactivespring.repository.TaxYearRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
class TaxYearHandlerAndRouterTests {
    @Autowired
    private WebTestClient client;

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
                  .doOnNext(System.out::println)
                  .blockLast();
    }
    @Test
    void testGetAllTaxYearInformationFiles() {
        client.get().uri("/tax-information-files")
              .accept(MediaType.APPLICATION_JSON)
              .exchange()
              .expectStatus().isOk()
              .expectHeader().contentType(MediaType.APPLICATION_JSON)
              .expectBodyList(TaxInformation.class);
    }

    @Test
    void testGetTaxYearInformation() {


        client.get()
              .uri("/tax-information-file?year={path}", taxInformationList.get(0).getYear())
              .exchange()
              .expectStatus().isOk()
              .expectBody()
              .consumeWith(response ->
                                   Assertions.assertThat(response.getResponseBody()).isNotNull());
    }
}