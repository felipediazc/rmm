package com.ninjaone.rmm;

import com.ninjaone.rmm.controller.DeviceTypesController;
import com.ninjaone.rmm.controller.DevicesController;
import com.ninjaone.rmm.db.entity.Device;
import com.ninjaone.rmm.db.entity.Devicetype;
import com.ninjaone.rmm.dto.DeviceDTO;
import com.ninjaone.rmm.dto.DeviceTypeDTO;
import com.ninjaone.rmm.dto.DeviceUpdateDTO;
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
public class DevicesTest {

    @Autowired
    DevicesController devicesController;

    @Autowired
    DeviceTypesController deviceTypesController;

    @Test
    void controllerMethodsTests() {

        DeviceTypeDTO deviceTypeDTO = new DeviceTypeDTO();
        deviceTypeDTO.setName("windows");
        Devicetype devicetype = deviceTypesController.addDeviceType(deviceTypeDTO);

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setSystemName("Windows 10 Home");
        deviceDTO.setDeviceType(devicetype.getName());
        Device device = devicesController.addDevice(deviceDTO);
        assertEquals("WINDOWS 10 HOME", device.getSystemname());

        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            devicesController.addDevice(deviceDTO);
        });

        DeviceUpdateDTO deviceUpdateDTO = new DeviceUpdateDTO();
        deviceUpdateDTO.setDeviceType(devicetype.getName());
        deviceUpdateDTO.setId(1);
        deviceUpdateDTO.setSystemName("Windows 10 Pro");
        Device deviceUpdated = devicesController.updateDevice(deviceUpdateDTO);
        assertEquals("WINDOWS 10 PRO", deviceUpdated.getSystemname());
        assertEquals(1, deviceUpdated.getId());

        Device deviceQuery = devicesController.getDevice(1);
        assertEquals("WINDOWS 10 PRO", deviceQuery.getSystemname());
        assertEquals(1, deviceQuery.getId());

        List<Device> deviceList = devicesController.getDevice();
        assertEquals(1, deviceList.size());
        assertEquals("WINDOWS 10 PRO", deviceList.get(0).getSystemname());
        assertEquals("WINDOWS", deviceList.get(0).getDevicetype().getName());

        assertDoesNotThrow(() -> devicesController.deleteDevice(1));
        assertThrows(RecordNotFoundException.class, () -> {
            devicesController.getDevice(1);
        });
    }

    @Test
    void failureMethodsTests() {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setSystemName("Windows 10 Home");
        deviceDTO.setDeviceType("TYPE");
        assertThrows(RecordNotFoundException.class, () -> {
            devicesController.addDevice(deviceDTO);
        });
        DeviceUpdateDTO deviceUpdateDTO = new DeviceUpdateDTO();
        deviceUpdateDTO.setDeviceType("WINDOWS");
        deviceUpdateDTO.setId(4);
        deviceUpdateDTO.setSystemName("Windows 10 Pro");
        assertThrows(RecordNotFoundException.class, () -> {
            devicesController.updateDevice(deviceUpdateDTO);
        });
        assertThrows(RecordNotFoundException.class, () -> {
            devicesController.deleteDevice(3);
        });
    }
}
