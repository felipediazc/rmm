package com.ninjaone.rmm;

import com.ninjaone.rmm.controller.DeviceTypesController;
import com.ninjaone.rmm.db.entity.Devicetype;
import com.ninjaone.rmm.dto.DeviceTypeDTO;
import com.ninjaone.rmm.dto.DeviceTypeUpdateDTO;
import com.ninjaone.rmm.exception.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DeviceTypeTest {

    @Autowired
    DeviceTypesController deviceTypesController;

    @Test
    void controllerMethodsTests() {

        DeviceTypeDTO deviceTypeDTO = new DeviceTypeDTO();
        deviceTypeDTO.setName("windows");
        Devicetype devicetype = deviceTypesController.addDeviceType(deviceTypeDTO);
        assertEquals("WINDOWS", devicetype.getName());
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            deviceTypesController.addDeviceType(deviceTypeDTO);
        });
        DeviceTypeUpdateDTO deviceTypeUpdateDTO = new DeviceTypeUpdateDTO();
        deviceTypeUpdateDTO.setName("windows xx");
        deviceTypeUpdateDTO.setId(1);
        devicetype = deviceTypesController.updateDeviceType(deviceTypeUpdateDTO);
        assertEquals("WINDOWS XX", devicetype.getName());

        Devicetype devicetypeQuery = deviceTypesController.getDeviceType(1);
        assertEquals(devicetype.getName(), devicetypeQuery.getName());
        assertEquals(devicetype.getId(), devicetypeQuery.getId());

        List<Devicetype> devicetypeList = deviceTypesController.getDeviceType();
        assertEquals(1, devicetypeList.size());
        assertEquals("WINDOWS XX", devicetypeList.get(0).getName());
    }

    @Test
    void failureMethodsTests() {
        assertThrows(RecordNotFoundException.class, () -> {
            deviceTypesController.getDeviceType(10);
        });
    }
}
