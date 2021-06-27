package com.byond.shifts.service.schedule.bus;

import com.byond.shifts.service.schedule.Schedule;
import com.byond.shifts.service.schedule.bus.dto.AddBus;
import com.byond.shifts.service.schedule.bus.dto.ViewBusScheduler;
import com.byond.shifts.service.shared.http.utility.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Table(name = "bus")
public class Bus {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "chasseNumber", unique = true, nullable = false)
    private long chasseNumber;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Getter
    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @MapKey(name = "id")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bus")
    private Map<Integer, Schedule> schedules;

    public Bus(AddBus dto) {
        chasseNumber = dto.getChasseNumber();
        capacity = dto.getCapacity();
        model = dto.getModel();
        make = dto.getMake();
        createdDate = new Date();
        schedules = new HashMap<>();
    }

    public ViewBusScheduler getPage(Page<Schedule> page) {
        ViewBusScheduler out = new ViewBusScheduler().setTotalPages(page.getTotalPages())
                                                     .setTotalElements(page.getTotalElements())
                                                     .setCapacity(capacity)
                                                     .setModel(model)
                                                     .setMake(make)
                                                     .setCreatedDate(DateUtil.formatDate(createdDate, "dd-MM-yyyy"))
                                                     .setUpdatedDate(updatedDate != null ? DateUtil.formatDate(updatedDate, "dd-MM-yyyy") : null);
        out.setDetails(page.getContent()
                           .stream()
                           .map(Schedule::viewBusDetails)
                           .collect(Collectors.toList()));
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bus)) return false;
        Bus bus = (Bus) o;
        return getChasseNumber() == bus.getChasseNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChasseNumber());
    }
}
