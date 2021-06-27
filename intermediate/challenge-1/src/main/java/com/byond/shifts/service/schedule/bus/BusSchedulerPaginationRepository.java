package com.byond.shifts.service.schedule.bus;

import com.byond.shifts.service.schedule.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;

@Repository
public interface BusSchedulerPaginationRepository extends PagingAndSortingRepository<Schedule, Integer> {
    Page<Schedule> findAllByBusAndDayOfWeek(Bus bus, DayOfWeek dayOfWeek, Pageable pageable);
}
