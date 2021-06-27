package com.byond.shifts.service.schedule;

import com.byond.shifts.service.schedule.bus.Bus;
import com.byond.shifts.service.schedule.driver.Driver;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Accessors(chain = true)
public class AddSchedulerConverter {
    private DayOfWeek dayOfWeek;
    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime finishTime;
    private Bus bus;
    private Driver driver;
}
