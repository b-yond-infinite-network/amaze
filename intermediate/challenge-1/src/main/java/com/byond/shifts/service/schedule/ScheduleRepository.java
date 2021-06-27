package com.byond.shifts.service.schedule;

import com.byond.shifts.service.schedule.bus.Bus;
import com.byond.shifts.service.schedule.driver.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Optional<Schedule> findByDayOfWeekAndScheduleDateAndStartTimeAndFinishTimeAndBusAndDriver(DayOfWeek dayOfWeek, LocalDate scheduleDate, LocalTime startTime, LocalTime finishTime, Bus bus, Driver driver);
}