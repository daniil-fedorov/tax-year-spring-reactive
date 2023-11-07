package com.oreilly.reactiveofficers.controllers;

import com.oreilly.reactiveofficers.entities.TaxInformation;
import com.oreilly.reactiveofficers.repository.TaxYearRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(TaxYearController.class)
class TaxYearControllerWithMocksTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private TaxYearRepository repository;

    private final List<TaxInformation> taxInformationList = Arrays.asList(
        TaxInformation.builder().year(2022).standardPersonalAllowance(12570).build(),
        TaxInformation.builder().year(2023).standardPersonalAllowance(12570).build()
    );

    @Test
    void testGetAllTaxInformationFiles() {
        given(repository.findAll())
                .willReturn(Flux.fromIterable(taxInformationList));
        client.get().uri("/tax-information-files")
              .accept(MediaType.APPLICATION_JSON)
              .exchange()
              .expectStatus().isOk()
              .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
              .expectBodyList(TaxInformation.class)
              .hasSize(2)
              .consumeWith(System.out::println);
    }

}
