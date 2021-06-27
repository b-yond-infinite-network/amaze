package com.byond.shifts.service.schedule.bus.dto;

import com.byond.shifts.service.shared.http.annotation.number.IntegerValue;
import com.byond.shifts.service.shared.http.annotation.number.LongValue;
import com.byond.shifts.service.shared.http.annotation.string.StringValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AddBus {
    @LongValue(isRequired = true)
    private long chasseNumber;

    @IntegerValue(isRequired = true)
    private Integer capacity;

    @StringValue(isRequired = true)
    private String model;

    @StringValue(isRequired = true)
    private String make;
}