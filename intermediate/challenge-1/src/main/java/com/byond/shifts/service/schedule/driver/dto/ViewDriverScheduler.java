package com.byond.shifts.service.schedule.driver.dto;

import com.byond.shifts.service.shared.http.dto.ClientData;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ViewDriverScheduler extends ClientData {
    private String firstName;
    private String lastName;
    private String createdDate;
    private String updatedDate;
    private int totalPages;
    private long totalElements;
    private List<ViewDriverSchedulerDetails> details;
}
