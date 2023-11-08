package com.taxyear.reactivespring.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@JsonDeserialize(builder = Band.BandBuilder.class)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public final class Band {

    private final int lowerBound;
    private final int upperBound;
    private final int percentage;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class BandBuilder { }

}
