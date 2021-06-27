package com.byond.shifts.service.shared.http.dto;

import com.byond.shifts.service.shared.http.enums.StatusCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException {
    protected StatusCode statusCode;
    protected String logMessage;
    protected String clientMessage;
    protected Object[] parameters;

    public ApplicationException(StatusCode statusCode, String logMessage, Object... param) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
        this.logMessage = logMessage;
        this.clientMessage = statusCode.getMessage();
        this.parameters = param;
    }
}
