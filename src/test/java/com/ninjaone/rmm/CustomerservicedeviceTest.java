package com.ninjaone.rmm;

import com.ninjaone.rmm.controller.CustomerServicesDevicesController;
import com.ninjaone.rmm.db.entity.Customer;
import com.ninjaone.rmm.db.entity.Customerservicesdevice;
import com.ninjaone.rmm.db.repository.CustomerRepository;
import com.ninjaone.rmm.dto.CustomerServicesDTO;
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
public class CustomerservicedeviceTest {

    @Autowired
    CustomerServicesDevicesController customerServicesDevicesController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void controllerMethodsTests() {
        Customer customer = new Customer();
        customer.setName("FELIPE DIAZ");
        customerRepository.save(customer);
        List<Customerservicesdevice> customerservicesdeviceList = customerServicesDevicesController.getCustomerServicesDevices(1);
        assertEquals(0, customerservicesdeviceList.size());
    }

    @Test
    void failureMethodsTests() {

        Customer customer = new Customer();
        customer.setName("FELIPE DIAZ");
        customerRepository.save(customer);

        CustomerServicesDTO customerServicesDTO = new CustomerServicesDTO();
        customerServicesDTO.setCustomer(2);
        customerServicesDTO.setCost(2);

        assertThrows(RecordNotFoundException.class, () -> {
            customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        });
        customerServicesDTO.setCustomer(1);
        assertThrows(RecordNotFoundException.class, () -> {
            customerServicesDevicesController.addCustomerServicesDevices(customerServicesDTO);
        });
        assertThrows(RecordNotFoundException.class, () -> {
            customerServicesDevicesController.getCustomerServicesDevices(2);
        });
        assertThrows(RecordNotFoundException.class, () -> {
            customerServicesDevicesController.deleteCustomerServicesDevices(0);
        });
        assertThrows(RecordNotFoundException.class, () -> {
            customerServicesDevicesController.getTotalMonthlyCost(2);
        });
    }
}
