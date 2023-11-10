package com.taxyear.reactivespring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxyear.reactivespring.entities.TaxInformation;
import com.taxyear.reactivespring.exception.TaxRepositoryException;
import com.taxyear.reactivespring.repository.TaxYearRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

@Slf4j
@Component
public class TaxYearInit implements ApplicationRunner {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final TaxYearRepository taxYearRepository;

    public TaxYearInit(TaxYearRepository taxYearRepository) {
        this.taxYearRepository = taxYearRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("taxYearData.json")) {

            String mockDBString = new String(requireNonNull(resourceAsStream).readAllBytes(), UTF_8);
            HashMap<String, TaxInformation> taxYearInformationMap = objectMapper.readValue(mockDBString, new TypeReference<Map<String, TaxInformation>>() { });

            taxYearRepository.deleteAll()
                .thenMany(Flux.fromIterable(taxYearInformationMap.values()))
                .flatMap(taxYearRepository::save)
                .thenMany(taxYearRepository.findAll())
                .subscribe(x -> log.info(x.toString()));
        } catch (IOException e) {
            throw new TaxRepositoryException(e);
        }
    }
}
