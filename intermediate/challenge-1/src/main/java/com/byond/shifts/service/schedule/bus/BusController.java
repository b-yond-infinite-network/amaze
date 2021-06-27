package com.byond.shifts.service.schedule.bus;

import com.byond.shifts.service.schedule.bus.dto.AddBus;
import com.byond.shifts.service.schedule.bus.dto.ViewBusScheduler;
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
@RequestMapping("/bus")
public class BusController {
    private final BusService busService;

    @PostMapping
    public ClientResponse<ClientData> save(@RequestBody @Valid AddBus request) {
        busService.add(request);
        return new ClientResponse<>(StatusCode.SUCCESS);
    }

    @GetMapping("/{chasse}")
    public ClientResponse<ViewBusScheduler> getDetails(@Valid @PathVariable("chasse") @LongValue(isRequired = true) Long chasse,
                                                       @RequestParam(name = "dayOfWeek") DayOfWeek dayOfWeek,
                                                       @RequestParam(name = "pageIndex") Integer pageIndex,
                                                       @RequestParam(name = "pageSize") Integer pageSize) {
        return new ClientResponse<>(StatusCode.SUCCESS, busService.getScheduler(chasse, dayOfWeek, pageIndex, pageSize));
    }
}