package com.byond.shifts.service.schedule.driver;

import com.byond.shifts.service.schedule.Schedule;
import com.byond.shifts.service.schedule.driver.dto.AddDriver;
import com.byond.shifts.service.schedule.driver.dto.ViewDriverScheduler;
import com.byond.shifts.service.shared.http.dto.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

import static com.byond.shifts.service.shared.http.enums.StatusCode.DUPLICATE_RECORD;
import static com.byond.shifts.service.shared.http.enums.StatusCode.RECORD_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverSchedulerPaginationRepository driverSchedulerPaginationRepository;

    public void add(AddDriver request) {
        //check if we have duplication
        driverRepository.findBySocialSecurityNumber(request.getSocialSecurityNumber())
                        .stream()
                        .findAny()
                        .ifPresent(s -> {
                            throw new ApplicationException(DUPLICATE_RECORD, "duplicate value in driver entity with ssn {} ", request.getSocialSecurityNumber());
                        });

        //save the new record
        driverRepository.save(new Driver(request));

        //log to console
        log.info("save new driver {} {} {}", request.getFirstName(), request.getLastName(), request.getSocialSecurityNumber());
    }

    public ViewDriverScheduler getScheduler(long socialSecurityNumber, DayOfWeek dayOfWeek, int pageIndex, int pageSize) {
        //get the driver
        Driver bus = getDriver(socialSecurityNumber);

        //get the data
        Page<Schedule> page = driverSchedulerPaginationRepository.findAllByDriverAndDayOfWeek(bus, dayOfWeek, PageRequest.of(pageIndex, pageSize));

        //convert the data
        return bus.getPage(page);
    }

    @Cacheable(value = "driver", key = "{#socialSecurityNumber}")
    public Driver getDriver(long socialSecurityNumber) {
        return driverRepository.findBySocialSecurityNumber(socialSecurityNumber)
                               .stream()
                               .findAny()
                               .orElseThrow(() -> new ApplicationException(RECORD_NOT_FOUND, "driver with social security number {} not found", socialSecurityNumber));
    }
}
