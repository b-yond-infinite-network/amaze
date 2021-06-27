package com.byond.shifts.service.schedule.driver;

import com.byond.shifts.service.schedule.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;

@Repository
public interface DriverSchedulerPaginationRepository extends PagingAndSortingRepository<Schedule, Integer> {
    Page<Schedule> findAllByDriverAndDayOfWeek(Driver driver, DayOfWeek dayOfWeek, Pageable pageable);
}
