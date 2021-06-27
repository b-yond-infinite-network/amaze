package com.byond.shifts.service.schedule;

import com.byond.shifts.service.schedule.bus.Bus;
import com.byond.shifts.service.schedule.bus.BusService;
import com.byond.shifts.service.schedule.driver.Driver;
import com.byond.shifts.service.schedule.driver.DriverService;
import com.byond.shifts.service.schedule.dto.AddSchedule;
import com.byond.shifts.service.shared.http.dto.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.byond.shifts.service.shared.http.enums.StatusCode.DUPLICATE_RECORD;

@Slf4j
@Service
@AllArgsConstructor
public class ScheduleService {
    private final BusService busService;
    private final DriverService driverService;
    private final ScheduleRepository scheduleRepository;

    public void add(AddSchedule request) {
        //find if we have available bus
        Bus bus = busService.getBus(request.getBus());

        //find if we have available driver
        Driver driver = driverService.getDriver(request.getDriver());

        //convert string values to real values
        AddSchedulerConverter schedulerConverter = request.convert(bus, driver);

        //check if we don't have conflict with other scheduler
        scheduleRepository.findByDayOfWeekAndScheduleDateAndStartTimeAndFinishTimeAndBusAndDriver(
                schedulerConverter.getDayOfWeek(),
                schedulerConverter.getScheduleDate(),
                schedulerConverter.getStartTime(),
                schedulerConverter.getFinishTime(),
                bus,
                driver
        )
                          .stream()
                          .findAny()
                          .ifPresent(s -> {
                              throw new ApplicationException(DUPLICATE_RECORD,
                                      "duplicate values in schedule entity for bus chasse {} with driver ssn {} on {} ",
                                      request.getBus(), request.getDriver(), request.getScheduleDate());
                          });

        //save new scheduler
        scheduleRepository.save(new Schedule(schedulerConverter));

        //log to console
        log.info("save new scheduler on {} start {} end  {} for bus {} and driver {}", request.getScheduleDate(), request.getStartTime(), request.getFinishTime(), bus.getChasseNumber(), driver.getSocialSecurityNumber());
    }
}