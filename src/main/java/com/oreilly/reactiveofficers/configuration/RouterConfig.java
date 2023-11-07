package com.oreilly.reactiveofficers.configuration;

import com.oreilly.reactiveofficers.controllers.TaxYearHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> route(TaxYearHandler handler) {
        return RouterFunctions
            .route(GET("/tax-information-file").and(accept(APPLICATION_JSON)),
                handler::getTaxYear)
            .andRoute(GET("/tax-information-files").and(accept(APPLICATION_JSON)),
                handler::getAllTaxYear);
    }
}
