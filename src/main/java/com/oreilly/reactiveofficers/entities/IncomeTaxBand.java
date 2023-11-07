package com.oreilly.reactiveofficers.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@JsonDeserialize(builder = IncomeTaxBand.IncomeTaxBandBuilder.class)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public final class IncomeTaxBand {

    private final Country country;
    private final List<Band> bands;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class IncomeTaxBandBuilder { }

}
