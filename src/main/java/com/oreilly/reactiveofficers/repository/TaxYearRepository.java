package com.oreilly.reactiveofficers.repository;

import com.oreilly.reactiveofficers.entities.TaxInformation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TaxYearRepository extends ReactiveMongoRepository<TaxInformation, String> {
    Mono<TaxInformation> findByYear(int year);
}
