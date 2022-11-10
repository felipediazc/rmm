package com.ninjaone.rmm.init;

import com.ninjaone.rmm.controller.*;
import com.ninjaone.rmm.db.entity.Cost;
import com.ninjaone.rmm.db.entity.Customer;
import com.ninjaone.rmm.db.repository.CustomerRepository;
import com.ninjaone.rmm.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SetupApplicationInitialData {

    @Autowired
    DeviceTypesController deviceTypesController;

    @Autowired
    DevicesController devicesController;

    @Autowired
    ServicesController servicesController;

    @Autowired
    CostsController costsController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerServicesDevicesController customerServicesDevicesController;

    public void setupData() {
        log.info("************************** SETTING INITIAL DATA ***********************");

        DeviceTypeDTO deviceTypeDTO = new DeviceTypeDTO();
        deviceTypeDTO.setName("windows");
        deviceTypesController.addDeviceType(deviceTypeDTO);
        deviceTypeDTO.setName("linux");
        deviceTypesController.addDeviceType(deviceTypeDTO);
        deviceTypeDTO.setName("mac");
        deviceTypesController.addDeviceType(deviceTypeDTO);

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setSystemName("Windows 10 Home");
        deviceDTO.setDeviceType("WINDOWS");
        devicesController.addDevice(deviceDTO);
        deviceDTO.setSystemName("IMAC 5K");
        deviceDTO.setDeviceType("MAC");
        devicesController.addDevice(deviceDTO);

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setServicename("antivirus");
        servicesController.addService(serviceDTO);
        serviceDTO.setServicename("backup");
        servicesController.addService(serviceDTO);
        serviceDTO.setServicename("psa");
        servicesController.addService(serviceDTO);
        serviceDTO.setServicename("screen share");
        servicesController.addService(serviceDTO);

        /*cost for each device*/
        CostDTO costServiceDTO = new CostDTO();
        costServiceDTO.setCost(4.0);
        costServiceDTO.setDevice("IMAC 5K");
        Cost imacDeviceCost = costsController.addCost(costServiceDTO);
        costServiceDTO.setDevice("WINDOWS 10 HOME");
        Cost windpowsDeviceCost = costsController.addCost(costServiceDTO);
        /* cost antivirus for windows */
        costServiceDTO = new CostDTO();
        costServiceDTO.setCost(5.0);
        costServiceDTO.setService("ANTIVIRUS");
        costServiceDTO.setServiceDeviceType("WINDOWS");
        Cost windowsAntivirusServiceCost = costsController.addCost(costServiceDTO);
        /* cost antivirus for mac */
        costServiceDTO.setCost(7.0);
        costServiceDTO.setService("ANTIVIRUS");
        costServiceDTO.setServiceDeviceType("MAC");
        Cost macAntivirusServiceCost = costsController.addCost(costServiceDTO);
        /* backup cost per device */
        costServiceDTO.setCost(3.0);
        costServiceDTO.setService("BACKUP");
        costServiceDTO.setServiceDeviceType("MAC");
        Cost macBackupServiceCost = costsController.addCost(costServiceDTO);
        costServiceDTO.setServiceDeviceType("WINDOWS");
        Cost windowsBackupServiceCost = costsController.addCost(costServiceDTO);
        /* psa cost per device */
        costServiceDTO.setCost(2.0);
        costServiceDTO.setService("PSA");
        costServiceDTO.setServiceDeviceType("MAC");
        costsController.addCost(costServiceDTO);
        costServiceDTO.setServiceDeviceType("WINDOWS");
        costsController.addCost(costServiceDTO);
        /* screen share cost per device */
        costServiceDTO.setCost(1.0);
        costServiceDTO.setService("SCREEN SHARE");
        costServiceDTO.setServiceDeviceType("MAC");
        Cost macScreenShareServiceCost = costsController.addCost(costServiceDTO);
        costServiceDTO.setServiceDeviceType("WINDOWS");
        Cost windowsScreenShareServiceCost = costsController.addCost(costServiceDTO);

        /* SETUP RESOURCES BY CUSTOMER */
        Customer customer = new Customer();
        customer.setName("FELIPE DIAZ");
        customerRepository.save(customer);
        CustomerServicesDTO customerServicesDTO = new CustomerServicesDTO();
        customerServicesDTO.setCustomer(customer.getId());
        customerServicesDTO.setCost(windpowsDeviceCost.getId());
        /* add 2 windows to customer */
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        /* add 3 mac to customer */
        customerServicesDTO.setCost(imacDeviceCost.getId());
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        /* add 3 antivirus for mac*/
        customerServicesDTO.setCost(macAntivirusServiceCost.getId());
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        /* add 3 backup for mac*/
        customerServicesDTO.setCost(macBackupServiceCost.getId());
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        /* add 3 screen share for mac */
        customerServicesDTO.setCost(macScreenShareServiceCost.getId());
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        /* add 2 antivirus for windows */
        customerServicesDTO.setCost(windowsAntivirusServiceCost.getId());
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        /* add 2 backup for windows */
        customerServicesDTO.setCost(windowsBackupServiceCost.getId());
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        /* add 2 screen share for windows */
        customerServicesDTO.setCost(windowsScreenShareServiceCost.getId());
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
    }
}