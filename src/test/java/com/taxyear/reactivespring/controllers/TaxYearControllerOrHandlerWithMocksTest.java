package com.taxyear.reactivespring.controllers;

import com.taxyear.reactivespring.entities.TaxInformation;
import com.taxyear.reactivespring.repository.TaxYearRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebFluxTest(TaxYearController.class)
class TaxYearControllerOrHandlerWithMocksTest {
    private final List<TaxInformation> taxInformationList = Arrays.asList(
        TaxInformation.builder().year(2022).standardPersonalAllowance(12570).build(),
        TaxInformation.builder().year(2023).standardPersonalAllowance(12570).build()
    );
    @Autowired
    private WebTestClient client;
    @MockBean
    private TaxYearRepository repository;

    @Test
    void testGetAllTaxInformationFiles() {
        given(repository.findAll())
            .willReturn(Flux.fromIterable(taxInformationList));
        client.get().uri("/tax-information-files")
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(APPLICATION_JSON_UTF8)
            .expectBodyList(TaxInformation.class)
            .hasSize(2)
            .consumeWith(x -> log.info(x.toString()));
    }

    @Test
    void testGetTaxInformationFile() {
        given(repository.findByYear(2022))
            .willReturn(Mono.just(taxInformationList.get(0)));
        client.get().uri("/tax-information-file?year={path}", taxInformationList.get(0).getYear())
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(APPLICATION_JSON)
            .expectBody(TaxInformation.class)
            .consumeWith(x -> log.info(x.toString()));
    }

}
