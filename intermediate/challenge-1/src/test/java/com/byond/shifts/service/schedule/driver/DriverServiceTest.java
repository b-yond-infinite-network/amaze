package com.byond.shifts.service.schedule.driver;

import com.byond.shifts.service.schedule.driver.dto.AddDriver;
import com.byond.shifts.service.shared.http.dto.ApplicationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {
    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DriverSchedulerPaginationRepository driverSchedulerPaginationRepository;

    @InjectMocks
    private DriverService driverService;

    @Test
    void addWithDuplicate() {
        Driver driver = new Driver();
        driver.setSocialSecurityNumber(2354646575689769L);
        doReturn(Optional.of(driver)).when(driverRepository)
                                     .findBySocialSecurityNumber(2354646575689769L);

        AddDriver dto = new AddDriver().setFirstName("Jhon")
                                       .setLastName("Smith")
                                       .setSocialSecurityNumber(2354646575689769L);

        assertThrows(ApplicationException.class, () -> driverService.add(dto));
    }
}
