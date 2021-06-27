package com.byond.shifts.service.schedule.driver;

import com.byond.shifts.service.schedule.driver.dto.AddDriver;
import com.byond.shifts.service.schedule.driver.dto.ViewDriverScheduler;
import com.byond.shifts.service.shared.http.annotation.number.LongValue;
import com.byond.shifts.service.shared.http.dto.ClientData;
import com.byond.shifts.service.shared.http.dto.ClientResponse;
import com.byond.shifts.service.shared.http.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/driver")
public class DriverController {
    private final DriverService driverService;

    @PostMapping
    public ClientResponse<ClientData> save(@RequestBody @Valid AddDriver request) {
        driverService.add(request);
        return new ClientResponse<>(StatusCode.SUCCESS);
    }

    @GetMapping("/{socialSecurityNumber}")
    public ClientResponse<ViewDriverScheduler> getDetails(@Valid @PathVariable("socialSecurityNumber") @LongValue(isRequired = true) Long socialSecurityNumber,
                                                          @RequestParam(name = "dayOfWeek") DayOfWeek dayOfWeek,
                                                          @RequestParam(name = "pageIndex") Integer pageIndex,
                                                          @RequestParam(name = "pageSize") Integer pageSize) {
        return new ClientResponse<>(StatusCode.SUCCESS, driverService.getScheduler(socialSecurityNumber, dayOfWeek, pageIndex, pageSize));
    }
}
