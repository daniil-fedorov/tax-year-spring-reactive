package com.taxyear.reactivespring.controllers;

import com.taxyear.reactivespring.entities.TaxInformation;
import com.taxyear.reactivespring.repository.TaxYearRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static com.taxyear.reactivespring.exception.ExceptionMessages.INVALID_TAX_YEAR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaxYearControllerOrHandlerTest {

    private final List<TaxInformation> taxInformationList = Arrays.asList(
        TaxInformation.builder().year(2022).standardPersonalAllowance(12570).build(),
        TaxInformation.builder().year(2023).standardPersonalAllowance(12570).build()
    );
    @Autowired
    private WebTestClient client;
    @Autowired
    private TaxYearRepository repository;

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
            .accept(APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(APPLICATION_JSON)
            .expectBodyList(TaxInformation.class)
            .hasSize(2);
    }

    @Test
    void testGetTaxYearInformation() {
        client.get()
            .uri("/tax-information-file?year={path}", taxInformationList.get(0).getYear())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .consumeWith(response ->
                assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    void testGetTaxYearInformationShouldReturnInvalidTaxYearMessageWhenNotFound() {
        client.get()
            .uri("/tax-information-file?year={path}", 0)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody(String.class)
            .consumeWith(response ->
                assertThat(response.getResponseBody()).isEqualTo(INVALID_TAX_YEAR_MESSAGE));
    }

    @Test
    void testGetTaxYearInformationShouldReturnInvalidTaxYearMessageWhenNoValuePresent() {
        client.get()
            .uri("/tax-information-file?year=")
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody(String.class)
            .consumeWith(response ->
                assertThat(response.getResponseBody()).isEqualTo(INVALID_TAX_YEAR_MESSAGE));
    }
}