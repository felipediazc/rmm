package com.ninjaone.rmm.controller;

import com.ninjaone.rmm.db.entity.Cost;
import com.ninjaone.rmm.db.entity.Customer;
import com.ninjaone.rmm.db.entity.Customerservicesdevice;
import com.ninjaone.rmm.db.repository.CostRepository;
import com.ninjaone.rmm.db.repository.CustomerRepository;
import com.ninjaone.rmm.db.repository.CustomerservicesdeviceRepository;
import com.ninjaone.rmm.dto.CustomerServicesDTO;
import com.ninjaone.rmm.dto.TotalMonthlyCostDTO;
import com.ninjaone.rmm.exception.ExceptionResponse;
import com.ninjaone.rmm.exception.RecordNotFoundException;
import com.ninjaone.rmm.service.MonthlyCosts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerServicesDevicesController {

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerservicesdeviceRepository customerservicesdeviceRepository;

    @Autowired
    private MonthlyCosts monthlyCosts;

    @PostMapping("/customerservicesdevices")
    @Operation(summary = "Add a record to the CUSTOMERSERVICESDEVICES table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Customerservicesdevice.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND. Foreign key not found).", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public Customerservicesdevice addCustomerServicesDevices(@Valid @RequestBody CustomerServicesDTO customerServicesDTO) {
        Optional<Customer> customerOptional = customerRepository.findById(customerServicesDTO.getCustomer());
        Optional<Cost> costOptional = costRepository.findById(customerServicesDTO.getCost());
        if (customerOptional.isPresent()) {
            if (costOptional.isPresent()) {
                Customerservicesdevice customerservicesdevice = new Customerservicesdevice();
                customerservicesdevice.setCustomer(customerOptional.get());
                customerservicesdevice.setCost(costOptional.get());
                customerservicesdeviceRepository.save(customerservicesdevice);
                return customerservicesdevice;
            }
            throw new RecordNotFoundException("There is no cost records with id " + customerServicesDTO.getCost());
        }
        throw new RecordNotFoundException("There is no customer records with id " + customerServicesDTO.getCustomer());
    }

    @GetMapping("/customerservicesdevices")
    @Operation(summary = "Get all records from the CUSTOMERSERVICESDEVICES table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public List<Customerservicesdevice> getCustomerServicesDevices() {
        return customerservicesdeviceRepository.findAllByOrderByIdAsc();
    }

    @GetMapping("/customerservicesdevices/{customerId}")
    @Operation(summary = "Get all records from the CUSTOMERSERVICESDEVICES filtered by customer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND. There is no records with customerid).", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public List<Customerservicesdevice> getCustomerServicesDevices(@Parameter(description = "customer ID (primary key of the CUSTOMERS table)") @PathVariable Integer customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            return customerservicesdeviceRepository.findAllByCustomer(customerOptional.get());
        }
        throw new RecordNotFoundException("There is no customer records with customer id " + customerId);
    }

    @DeleteMapping("/customerservicesdevices/{id}")
    @Operation(summary = "Deletes a record using its ID (CUSTOMERSERVICESDEVICES table primary key)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND. There is no records with id).", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public void deleteCustomerServicesDevices(@Parameter(description = "CUSTOMERSERVICESDEVICES table primary key") @PathVariable Integer id) {
        Optional<Customerservicesdevice> customerservicesdeviceOptional = customerservicesdeviceRepository.findById(id);
        if (customerservicesdeviceOptional.isPresent()) {
            Customerservicesdevice customerservicesdevice = customerservicesdeviceOptional.get();
            customerservicesdeviceRepository.delete(customerservicesdevice);
        } else {
            throw new RecordNotFoundException("There is no customerservicesdevice records with id " + id);
        }
    }

    @GetMapping("/customerservicesdevices/monthlycost/{customerid}")
    @Operation(summary = "Get customers total monthly cost of the services and devices it owns")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TotalMonthlyCostDTO.class)))
    })
    public TotalMonthlyCostDTO getTotalMonthlyCost(@Parameter(description = "customer ID (primary key of the CUSTOMERS table)") @PathVariable Integer customerid) {
        return monthlyCosts.getTotalMonthlyCost(customerid);
    }
}
