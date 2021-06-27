package com.byond.shifts.service.schedule.driver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ViewDriverSchedulerDetails {
    private String scheduleDate;
    private String startTime;
    private String finishTime;
    private String bus;
    private String createdDate;
    private String updatedDate;
}
