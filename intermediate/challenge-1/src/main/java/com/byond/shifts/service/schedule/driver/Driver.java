package com.byond.shifts.service.schedule.driver;

import com.byond.shifts.service.schedule.Schedule;
import com.byond.shifts.service.schedule.bus.dto.ViewBusScheduler;
import com.byond.shifts.service.schedule.driver.dto.AddDriver;
import com.byond.shifts.service.schedule.driver.dto.ViewDriverScheduler;
import com.byond.shifts.service.shared.http.utility.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Entity
@NoArgsConstructor
@Table(name = "driver")
public class Driver {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Getter
    @Setter
    @Column(name = "social_security_number", unique = true, nullable = false)
    private long socialSecurityNumber;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @MapKey(name = "id")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "driver")
    private Map<Integer, Schedule> schedules;

    public Driver(AddDriver request) {
        firstName = request.getFirstName();
        lastName = request.getLastName();
        socialSecurityNumber = request.getSocialSecurityNumber();
        createdDate = new Date();
        schedules = new HashMap<>();
    }

    public String getFullName(){
        return firstName+" "+lastName;
    }

    public ViewDriverScheduler getPage(Page<Schedule> page) {
        ViewDriverScheduler out = new ViewDriverScheduler().setTotalPages(page.getTotalPages())
                                                           .setTotalElements(page.getTotalElements())
                                                           .setFirstName(firstName)
                                                           .setLastName(lastName)
                                                           .setCreatedDate(DateUtil.formatDate(createdDate, "dd-MM-yyyy"))
                                                           .setUpdatedDate(updatedDate != null ? DateUtil.formatDate(updatedDate, "dd-MM-yyyy") : null);
        out.setDetails(page.getContent()
                           .stream()
                           .map(Schedule::viewDriverDetails)
                           .collect(Collectors.toList()));
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;
        Driver driver = (Driver) o;
        return getSocialSecurityNumber() == driver.getSocialSecurityNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSocialSecurityNumber());
    }
}