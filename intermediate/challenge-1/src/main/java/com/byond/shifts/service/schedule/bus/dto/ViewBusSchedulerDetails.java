package com.byond.shifts.service.schedule.bus.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ViewBusSchedulerDetails {
    private String scheduleDate;
    private String startTime;
    private String finishTime;
    private String driver;
    private String createdDate;
    private String updatedDate;
}
