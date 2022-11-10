package com.ninjaone.rmm;

import com.ninjaone.rmm.controller.ServicesController;
import com.ninjaone.rmm.db.entity.Service;
import com.ninjaone.rmm.dto.ServiceDTO;
import com.ninjaone.rmm.exception.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ServicesTest {

    @Autowired
    ServicesController servicesController;

    @Test
    void controllerMethodsTests() {

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setServicename("backup");
        Service service = servicesController.addService(serviceDTO);
        assertEquals("BACKUP", service.getServicename());
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            servicesController.addService(serviceDTO);
        });

        List<Service> serviceList = servicesController.getService();
        assertEquals(1, serviceList.size());
        assertEquals("BACKUP", serviceList.get(0).getServicename());

        Service testService = servicesController.getService(1);
        assertEquals("BACKUP", testService.getServicename());

        assertDoesNotThrow(() -> servicesController.deleteService(1));
        assertThrows(RecordNotFoundException.class, () -> {
            servicesController.getService(1);
        });
    }

    @Test
    void failureMethodsTests() {
        assertThrows(RecordNotFoundException.class, () -> {
            servicesController.deleteService(2);
        });
        assertThrows(RecordNotFoundException.class, () -> {
            servicesController.getService(2);
        });
    }
}
