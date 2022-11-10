package com.ninjaone.rmm.controller;

import com.ninjaone.rmm.db.entity.Service;
import com.ninjaone.rmm.db.repository.ServiceRepository;
import com.ninjaone.rmm.dto.ServiceDTO;
import com.ninjaone.rmm.exception.ExceptionResponse;
import com.ninjaone.rmm.exception.RecordNotFoundException;
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
public class ServicesController {
    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping("/service")
    @Operation(summary = "Add a record to the SERVICES table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public Service addService(@Valid @RequestBody ServiceDTO serviceDTO) {
        Service service = new Service();
        service.setServicename(serviceDTO.getServicename().toUpperCase());
        serviceRepository.save(service);
        return service;
    }

    @DeleteMapping("/service/{id}")
    @Operation(summary = "Deletes a record from the SERVICES table using its ID (SERVICES table primary key)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND. There is no records with id).", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public void deleteService(@Parameter(description = "SERVICES ID (primary key of the table)") @PathVariable Integer id) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isPresent()) {
            Service service = serviceOptional.get();
            serviceRepository.delete(service);
        } else {
            throw new RecordNotFoundException("There is no device records with id " + id);
        }
    }

    @GetMapping("/service")
    @Operation(summary = "Get all records from the DEVICES table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public List<Service> getService() {
        return serviceRepository.findAllByOrderByServicenameAsc();
    }

    @GetMapping("/service/{id}")
    @Operation(summary = "Get a record from the SERVICES table using its ID (SERVICES table primary key)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Service.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND. There is no records with id).", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public Service getService(@Parameter(description = "SERVICES ID (primary key of the table)") @PathVariable Integer id) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isPresent()) {
            return serviceOptional.get();
        }
        throw new RecordNotFoundException("There is no service records with id " + id);
    }
}
