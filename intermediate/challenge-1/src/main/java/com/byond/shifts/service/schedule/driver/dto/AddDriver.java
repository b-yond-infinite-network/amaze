package com.byond.shifts.service.schedule.driver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AddDriver {
    private String firstName;

    private String lastName;

    private long socialSecurityNumber;
}
