package com.byond.shifts.service.schedule.bus;

import com.byond.shifts.service.schedule.bus.dto.AddBus;
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
class BusServiceTest {
    @Mock
    private BusRepository busRepository;

    @Mock
    private BusSchedulerPaginationRepository busSchedulerPaginationRepository;

    @InjectMocks
    private BusService busService;

    @Test
    void addWithDuplicate() {
        Bus bus = new Bus();
        bus.setChasseNumber(45672342365L);
        doReturn(Optional.of(bus)).when(busRepository)
                                  .findByChasseNumber(45672342365L);

        AddBus dto = new AddBus().setCapacity(12)
                                 .setMake("Ford")
                                 .setChasseNumber(45672342365L)
                                 .setModel("2010");

        assertThrows(ApplicationException.class, () -> busService.add(dto));
    }
}