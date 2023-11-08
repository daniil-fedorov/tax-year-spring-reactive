package com.taxyear.reactivespring.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@JsonDeserialize(builder = TaxInformation.TaxInformationBuilder.class)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public final class TaxInformation {
    private final int year;
    private final int standardPersonalAllowance;
//    private final List<Band> nationalInsurance;
//    private final List<StudentPlan> studentLoanPlans;
//    private final int personalAllowanceReductionLevel;
//    private final double personalAllowanceReductionRate;
//    private final double basicRate;
//    private final double scottishBasicRate;
//    private final String englandDefaultTaxCode;
//    private final String scotlandDefaultTaxCode;
//    private final String walesDefaultTaxCode;
//    private final String northernIrelandDefaultTaxCode;
//    private final double bikTaxRate;
//    private final int pensionPersonalAllowance;
//    private final List<IncomeTaxBand> incomeTaxBands;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class TaxInformationBuilder { }
}
