package com.byond.shifts.service.schedule.dto;

import com.byond.shifts.service.schedule.AddSchedulerConverter;
import com.byond.shifts.service.schedule.bus.Bus;
import com.byond.shifts.service.schedule.driver.Driver;
import com.byond.shifts.service.shared.http.annotation.date.DateValue;
import com.byond.shifts.service.shared.http.annotation.date.TimeValue;
import com.byond.shifts.service.shared.http.annotation.number.LongValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AddSchedule {
    @JsonProperty(required = true)
    private DayOfWeek dayOfWeek;

    @DateValue(isRequired = true)
    private String scheduleDate;

    @TimeValue(isRequired = true)
    private String startTime;

    @TimeValue(isRequired = true)
    private String finishTime;

    @LongValue(isRequired = true)
    private Long bus;

    @LongValue(isRequired = true)
    private Long driver;


    public AddSchedulerConverter convert(Bus bus, Driver driver) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new AddSchedulerConverter().setDayOfWeek(dayOfWeek)
                                          .setScheduleDate(LocalDate.parse(scheduleDate, dateTimeFormatter))
                                          .setStartTime(LocalTime.parse(startTime))
                                          .setFinishTime(LocalTime.parse(finishTime))
                                          .setBus(bus)
                                          .setDriver(driver);
    }
}
