package com.ninjaone.rmm.controller;

import com.ninjaone.rmm.db.entity.*;
import com.ninjaone.rmm.db.repository.CostRepository;
import com.ninjaone.rmm.db.repository.DeviceRepository;
import com.ninjaone.rmm.db.repository.DevicetypeRepository;
import com.ninjaone.rmm.db.repository.ServiceRepository;
import com.ninjaone.rmm.dto.CostDTO;
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
public class CostsController {

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DevicetypeRepository devicetypeRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping("/cost")
    @Operation(summary = "Add a record to the COST table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Cost.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND. Foreing key not found).", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public Cost addCost(@Valid @RequestBody CostDTO costDTO) {

        if (costDTO.getService() != null) {
            String serviceTypeName = costDTO.getServiceDeviceType().toUpperCase();
            Devicetype devicetype = devicetypeRepository.findByName(serviceTypeName);
            if (devicetype == null) {
                throw new RecordNotFoundException("There is no devicetype records with name " + serviceTypeName);
            }

            String serviceName = costDTO.getService().toUpperCase();
            Service service = serviceRepository.findByServicename(serviceName);
            if (service == null) {
                throw new RecordNotFoundException("There is no service records with name " + serviceName);
            }
            Cost cost = new Cost();
            cost.setCost(costDTO.getCost());
            cost.setService(service);
            cost.setServicedevicetype(devicetype);
            costRepository.save(cost);
            return cost;
        } else {
            String deviceName = costDTO.getDevice().toUpperCase();
            Device device = deviceRepository.findBySystemname(deviceName);
            if (device == null) {
                throw new RecordNotFoundException("There is no device records with name " + deviceName);
            }
            Cost cost = new Cost();
            cost.setCost(costDTO.getCost());
            cost.setDevice(device);
            costRepository.save(cost);
            return cost;
        }
    }

    @GetMapping("/cost")
    @Operation(summary = "Get all record from the COST table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public List<Cost> getCosts() {
        return costRepository.findAllByOrderByIdAsc();
    }

    @DeleteMapping("/cost/{id}")
    @Operation(summary = "Deletes a record using its ID (COST table primary key)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND. There is no records with id).", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public void deleteCost(@Parameter(description = "COSTS table primary key") @PathVariable Integer id) {
        Optional<Cost> costOptional = costRepository.findById(id);
        if (costOptional.isPresent()) {
            Cost cost = costOptional.get();
            costRepository.delete(cost);
        } else {
            throw new RecordNotFoundException("There is no cost records with id " + id);
        }
    }
}
