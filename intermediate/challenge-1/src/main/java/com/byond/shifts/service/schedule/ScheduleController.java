package com.byond.shifts.service.schedule;

import com.byond.shifts.service.schedule.dto.AddSchedule;
import com.byond.shifts.service.shared.http.dto.ClientData;
import com.byond.shifts.service.shared.http.dto.ClientResponse;
import com.byond.shifts.service.shared.http.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ClientResponse<ClientData> save(@RequestBody @Valid AddSchedule request) {
        scheduleService.add(request);
        return new ClientResponse<>(StatusCode.SUCCESS);
    }
}
