package com.taxyear.reactivespring.repository;

import com.taxyear.reactivespring.entities.TaxInformation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TaxYearRepository extends ReactiveMongoRepository<TaxInformation, String> {
    Mono<TaxInformation> findByYear(int year);
}
