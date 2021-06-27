package com.byond.shifts.service.schedule.bus;

import com.byond.shifts.service.schedule.Schedule;
import com.byond.shifts.service.schedule.bus.dto.AddBus;
import com.byond.shifts.service.schedule.bus.dto.ViewBusScheduler;
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
public class BusService {
    private final BusRepository busRepository;
    private final BusSchedulerPaginationRepository busSchedulerPaginationRepository;

    public void add(AddBus request) {
        //check if we have duplication
        busRepository.findByChasseNumber(request.getChasseNumber())
                     .stream()
                     .findAny()
                     .ifPresent(s -> {
                         throw new ApplicationException(DUPLICATE_RECORD, "duplicate values in bus entity {} {} {} ", request.getCapacity(), request.getMake(), request.getModel());
                     });

        //save the new record
        busRepository.save(new Bus(request));

        //log to console
        log.info("save new bus {} {} {}", request.getCapacity(), request.getMake(), request.getModel());
    }

    public ViewBusScheduler getScheduler(long chasseNumber, DayOfWeek dayOfWeek, int pageIndex, int pageSize) {
        //get the bus
        Bus bus = getBus(chasseNumber);

        //get the data
        Page<Schedule> page = busSchedulerPaginationRepository.findAllByBusAndDayOfWeek(bus, dayOfWeek, PageRequest.of(pageIndex, pageSize));

        //convert the data
        return bus.getPage(page);
    }

    @Cacheable(value = "bus", key = "{#chasseNumber}")
    public Bus getBus(long chasseNumber) {
        return busRepository.findByChasseNumber(chasseNumber)
                            .stream()
                            .findAny()
                            .orElseThrow(() -> new ApplicationException(RECORD_NOT_FOUND, "bus {} not found", chasseNumber));
    }
}