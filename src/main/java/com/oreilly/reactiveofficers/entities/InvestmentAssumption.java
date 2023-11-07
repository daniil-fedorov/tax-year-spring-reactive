package com.oreilly.reactiveofficers.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@JsonDeserialize(builder = InvestmentAssumption.InvestmentAssumptionBuilder.class)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public final class InvestmentAssumption {

    private final double annualSalaryIncrease;
    private final double investmentReturnRate;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class InvestmentAssumptionBuilder { }
}
