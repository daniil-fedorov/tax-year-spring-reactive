package com.oreilly.reactiveofficers.controllers;

import com.oreilly.reactiveofficers.repository.TaxYearRepository;
import com.oreilly.reactiveofficers.entities.TaxInformation;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class TaxYearHandler {
    private final TaxYearRepository repository;

    public TaxYearHandler(TaxYearRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getTaxYear(ServerRequest request) {

        Optional<String> year = request.queryParam("year");
        return year.map(value -> ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(repository.findByYear(Integer.parseInt(value)), TaxInformation.class))

            .orElseGet(() -> ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> getAllTaxYear() {
        return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(repository.findAll(), TaxInformation.class);
    }
}
