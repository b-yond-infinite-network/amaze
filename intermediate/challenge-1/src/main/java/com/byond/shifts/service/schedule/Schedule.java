package com.byond.shifts.service.schedule;

import com.byond.shifts.service.schedule.bus.Bus;
import com.byond.shifts.service.schedule.bus.dto.ViewBusSchedulerDetails;
import com.byond.shifts.service.schedule.driver.Driver;
import com.byond.shifts.service.schedule.driver.dto.ViewDriverSchedulerDetails;
import com.byond.shifts.service.shared.http.utility.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Slf4j
@Entity
@NoArgsConstructor
@Table(name = "schedule", uniqueConstraints = {@UniqueConstraint(columnNames = {"day_of_week", "schedule_date", "start_time", "bus_id", "driver_id"})})
public class Schedule {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "schedule_date", nullable = false, columnDefinition = "DATE")
    private LocalDate scheduleDate;

    @Column(name = "start_time", nullable = false, columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "finish_time", nullable = false, columnDefinition = "TIME")
    private LocalTime finishTime;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    public Schedule(AddSchedulerConverter dto) {
        dayOfWeek = dto.getDayOfWeek();
        scheduleDate = dto.getScheduleDate();
        startTime = dto.getStartTime();
        finishTime = dto.getFinishTime();
        bus = dto.getBus();
        driver = dto.getDriver();
        createdDate = new Date();
    }

    public ViewBusSchedulerDetails viewBusDetails() {
        return new ViewBusSchedulerDetails().setScheduleDate(scheduleDate.toString())
                                            .setStartTime(startTime.toString())
                                            .setFinishTime(finishTime.toString())
                                            .setDriver(driver.getFullName())
                                            .setCreatedDate(DateUtil.formatDate(createdDate, "dd-MM-yyyy"))
                                            .setUpdatedDate(updatedDate != null ? DateUtil.formatDate(updatedDate, "dd-MM-yyyy") : null);
    }

    public ViewDriverSchedulerDetails viewDriverDetails() {
        return new ViewDriverSchedulerDetails().setScheduleDate(scheduleDate.toString())
                                               .setStartTime(startTime.toString())
                                               .setFinishTime(finishTime.toString())
                                               .setBus(bus.getModel())
                                               .setCreatedDate(DateUtil.formatDate(createdDate, "dd-MM-yyyy"))
                                               .setUpdatedDate(updatedDate != null ? DateUtil.formatDate(updatedDate, "dd-MM-yyyy") : null);
    }

    @Override
    public String toString() {
        return "Driver " + driver.getFullName() + ", will be driving Bus " + bus.getModel() + " on " + dayOfWeek + " between " + startTime + " and " + finishTime;
    }
}
