package com.oreilly.reactiveofficers.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@JsonDeserialize(builder = StudentPlan.StudentPlanBuilder.class)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public final class StudentPlan {

    private final StudentPlanEnum planName;
    private final List<Band> bands;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class StudentPlanBuilder { }

}
