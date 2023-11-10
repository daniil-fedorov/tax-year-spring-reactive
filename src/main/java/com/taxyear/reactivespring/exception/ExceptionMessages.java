package com.taxyear.reactivespring.exception;

public final class ExceptionMessages {

    public static final String INVALID_TAX_YEAR_MESSAGE = "Invalid year parameter. Year must be a valid tax year (e.g., 2021, 2022, 2023).";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error."; // no Data access
    private ExceptionMessages() {
    }
}
