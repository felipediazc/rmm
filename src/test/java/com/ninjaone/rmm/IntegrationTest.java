package com.ninjaone.rmm;

import com.ninjaone.rmm.controller.*;
import com.ninjaone.rmm.db.entity.*;
import com.ninjaone.rmm.db.repository.CustomerRepository;
import com.ninjaone.rmm.dto.*;
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
public class IntegrationTest {

    @Autowired
    DevicesController devicesController;

    @Autowired
    CostsController costsController;

    @Autowired
    ServicesController servicesController;

    @Autowired
    DeviceTypesController deviceTypesController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerServicesDevicesController customerServicesDevicesController;

    @Test
    void controllerMethodsTests() {

        Customer customer = new Customer();
        customer.setName("FELIPE DIAZ");
        customerRepository.save(customer);

        DeviceTypeDTO deviceTypeDTO = new DeviceTypeDTO();
        deviceTypeDTO.setName("windows");
        Devicetype devicetype = deviceTypesController.addDeviceType(deviceTypeDTO);

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setDeviceType("WINDOWS");
        deviceDTO.setSystemName("WINDOWS 10");
        Device device = devicesController.addDevice(deviceDTO);

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setServicename("backup");
        Service service = servicesController.addService(serviceDTO);

        CostDTO costServiceDTO = new CostDTO();
        costServiceDTO.setCost(12.0);
        costServiceDTO.setService(service.getServicename());
        costServiceDTO.setServiceDeviceType(devicetype.getName());
        Cost cost = costsController.addCost(costServiceDTO);

        costServiceDTO = new CostDTO();
        costServiceDTO.setCost(4.0);
        costServiceDTO.setDevice(device.getSystemname());
        Cost costDevice = costsController.addCost(costServiceDTO);

        final CustomerServicesDTO customerServicesDTO = new CustomerServicesDTO();
        customerServicesDTO.setCustomer(customer.getId());
        customerServicesDTO.setCost(cost.getId());
        assertDoesNotThrow(() -> customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO));

        CustomerServicesDTO customerServicesDeviceDTO = new CustomerServicesDTO();
        customerServicesDeviceDTO.setCustomer(customer.getId());
        customerServicesDeviceDTO.setCost(costDevice.getId());
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDeviceDTO);


        List<Customerservicesdevice> customerservicesdeviceList = customerServicesDevicesController.getCustomerServicesDevices();
        assertEquals(2, customerservicesdeviceList.size());
        assertNull(customerservicesdeviceList.get(0).getCost().getDevice());
        assertEquals("BACKUP", customerservicesdeviceList.get(0).getCost().getService().getServicename());
        assertEquals("WINDOWS", customerservicesdeviceList.get(0).getCost().getServicedevicetype().getName());
        assertEquals(12.0, customerservicesdeviceList.get(0).getCost().getCost());
        assertEquals("FELIPE DIAZ", customerservicesdeviceList.get(0).getCustomer().getName());

        TotalMonthlyCostDTO totalMonthlyCostDTO = customerServicesDevicesController.getTotalMonthlyCost(1);
        assertEquals(16.0, totalMonthlyCostDTO.getTotal());

        assertDoesNotThrow(() -> customerServicesDevicesController.deleteCustomerServicesDevices(1));

    }
}
