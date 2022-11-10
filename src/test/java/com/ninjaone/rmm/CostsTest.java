package com.ninjaone.rmm;

import com.ninjaone.rmm.controller.CostsController;
import com.ninjaone.rmm.controller.DeviceTypesController;
import com.ninjaone.rmm.controller.DevicesController;
import com.ninjaone.rmm.controller.ServicesController;
import com.ninjaone.rmm.db.entity.Cost;
import com.ninjaone.rmm.db.entity.Device;
import com.ninjaone.rmm.db.entity.Devicetype;
import com.ninjaone.rmm.db.entity.Service;
import com.ninjaone.rmm.dto.CostDTO;
import com.ninjaone.rmm.dto.DeviceDTO;
import com.ninjaone.rmm.dto.DeviceTypeDTO;
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
public class CostsTest {

    @Autowired
    CostsController costsController;

    @Autowired
    ServicesController servicesController;

    @Autowired
    DeviceTypesController deviceTypesController;

    @Autowired
    DevicesController devicesController;

    @Test
    void controllerMethodsTests() {

        DeviceTypeDTO deviceTypeDTO = new DeviceTypeDTO();
        deviceTypeDTO.setName("windows");
        Devicetype devicetype = deviceTypesController.addDeviceType(deviceTypeDTO);

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setSystemName("Windows 10 Home");
        deviceDTO.setDeviceType(devicetype.getName());
        Device device = devicesController.addDevice(deviceDTO);

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setServicename("backup");
        Service service = servicesController.addService(serviceDTO);

        final CostDTO costServiceDTO = new CostDTO();
        costServiceDTO.setCost(12.0);
        costServiceDTO.setService(service.getServicename());
        costServiceDTO.setServiceDeviceType(devicetype.getName());
        assertDoesNotThrow(() -> costsController.addCost(costServiceDTO));

        final CostDTO costDeviceDTO = new CostDTO();
        costDeviceDTO.setCost(4.0);
        costDeviceDTO.setDevice(device.getSystemname());
        assertDoesNotThrow(() -> costsController.addCost(costDeviceDTO));

        List<Cost> costsList = costsController.getCosts();
        assertEquals(2, costsList.size());

        assertDoesNotThrow(() -> costsController.deleteCost(1));
        costsList = costsController.getCosts();
        assertEquals("WINDOWS 10 HOME", costsList.get(0).getDevice().getSystemname());
    }

    @Test
    void failureMethodsTests() {
        DeviceTypeDTO deviceTypeDTO = new DeviceTypeDTO();
        deviceTypeDTO.setName("windows");
        Devicetype devicetype = deviceTypesController.addDeviceType(deviceTypeDTO);

        final CostDTO costServiceDTO = new CostDTO();
        costServiceDTO.setCost(12.0);
        costServiceDTO.setService("SERVICE");
        costServiceDTO.setServiceDeviceType("MAC");
        assertThrows(RecordNotFoundException.class, () -> {
            costsController.addCost(costServiceDTO);
        });
        costServiceDTO.setServiceDeviceType("WINDOWS");
        assertThrows(RecordNotFoundException.class, () -> {
            costsController.addCost(costServiceDTO);
        });
        CostDTO costDeviceDTO = new CostDTO();
        costDeviceDTO.setCost(12.0);
        costDeviceDTO.setDevice("MY DEVICE");
        assertThrows(RecordNotFoundException.class, () -> {
            costsController.addCost(costDeviceDTO);
        });
        assertThrows(RecordNotFoundException.class, () -> {
            costsController.deleteCost(5);
        });
    }

}
