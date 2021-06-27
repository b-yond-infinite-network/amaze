package com.byond.shifts.service.shared.http.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    /*
     * Entry For Success Scenario
     */
    SUCCESS(1000, "Success"),

    /*
     * Entry For Application Error
     */
    INVALID_PARAMETERS(2000, "Validation Failure"),
    INVALID_METHOD_ARGUMENTS(2001, "Invalid Method Argument"),
    HTTP_METHOD_NOT_SUPPORTED(2002, "HTTP Method Not Supported"),
    RECORD_NOT_FOUND(2003, "Record Not Found"),
    DUPLICATE_RECORD(2004, "Duplicate Record"),
    /*
     * Entry For UN_HANDLED Exceptions
     */
    INTERNAL_SERVER_ERROR(5000, "Internal Server Error");

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}