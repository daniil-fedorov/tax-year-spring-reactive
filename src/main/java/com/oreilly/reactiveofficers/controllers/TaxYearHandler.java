package com.oreilly.reactiveofficers.controllers;

import com.oreilly.reactiveofficers.repository.TaxYearRepository;
import com.oreilly.reactiveofficers.entities.TaxInformation;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class TaxYearHandler {
    private TaxYearRepository repository;

    public TaxYearHandler(TaxYearRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getTaxYear(ServerRequest request) {
        return ServerResponse.ok()
                             .contentType(APPLICATION_JSON)
                             .body(repository.findByYear(Integer.valueOf(request.queryParam("year").get())), TaxInformation.class);
    }

    public Mono<ServerResponse> getAllTaxYear(ServerRequest request) {
        return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(repository.findAll(), TaxInformation.class);
    }
}
