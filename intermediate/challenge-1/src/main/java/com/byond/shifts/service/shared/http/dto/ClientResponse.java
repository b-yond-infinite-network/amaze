package com.byond.shifts.service.shared.http.dto;

import com.byond.shifts.service.shared.http.enums.StatusCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClientResponse<T extends ClientData> {
    private String status;
    private Integer code;
    private T data;
    private List<Violation> violations;

    public ClientResponse(StatusCode code) {
        this.code = code.getCode();
        this.status = code.getMessage();
    }

    public ClientResponse(StatusCode code, T data) {
        this.data = data;
        this.code = code.getCode();
        this.status = code.getMessage();
    }

    public void addViolation(Violation violation) {
        if (violations == null)
            violations = new ArrayList<>();
        violations.add(violation);
    }
}
