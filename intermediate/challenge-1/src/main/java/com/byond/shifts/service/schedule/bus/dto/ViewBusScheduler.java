package com.byond.shifts.service.schedule.bus.dto;

import com.byond.shifts.service.shared.http.dto.ClientData;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ViewBusScheduler extends ClientData {
    private int capacity;
    private String model;
    private String make;
    private String createdDate;
    private String updatedDate;
    private int totalPages;
    private long totalElements;
    List<ViewBusSchedulerDetails> details;
}
