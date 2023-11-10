package com.taxyear.reactivespring.controllers;

import com.taxyear.reactivespring.entities.TaxInformation;
import com.taxyear.reactivespring.repository.TaxYearRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.taxyear.reactivespring.exception.ExceptionMessages.INVALID_TAX_YEAR_MESSAGE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class TaxYearHandler {
    private final TaxYearRepository repository;

    public TaxYearHandler(TaxYearRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getTaxYear(ServerRequest request) {
        String requestYear = request.queryParam("year").filter(x -> !x.isEmpty()).orElse("0");

        int year = Integer.parseInt(requestYear);

        return repository.findByYear(year).flatMap(value -> ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .syncBody(value))
            .switchIfEmpty(ServerResponse.badRequest().syncBody(INVALID_TAX_YEAR_MESSAGE));
    }

    public Mono<ServerResponse> getAllTaxYear() {
        return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(repository.findAll(), TaxInformation.class);
    }
}
