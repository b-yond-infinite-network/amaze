package com.byond.shifts.service.shared.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error extends ClientData {
    private String details;
}
