package com.ninjaone.rmm.controller;

import com.ninjaone.rmm.db.entity.Device;
import com.ninjaone.rmm.db.entity.Devicetype;
import com.ninjaone.rmm.db.repository.DeviceRepository;
import com.ninjaone.rmm.db.repository.DevicetypeRepository;
import com.ninjaone.rmm.dto.DeviceDTO;
import com.ninjaone.rmm.dto.DeviceUpdateDTO;
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
public class DevicesController {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DevicetypeRepository devicetypeRepository;

    @PostMapping("/device")
    @Operation(summary = "Add a record to the DEVICES table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND. There is no devicetype records with name).", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public Device addDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        String deviceTypeName = deviceDTO.getDeviceType().toUpperCase();
        Devicetype devicetype = devicetypeRepository.findByName(deviceTypeName);
        if (devicetype == null) {
            throw new RecordNotFoundException("There is no devicetype records with name " + deviceTypeName);
        }
        Device device = new Device();
        device.setSystemname(deviceDTO.getSystemName().toUpperCase());
        device.setDevicetype(devicetype);
        deviceRepository.save(device);
        return device;
    }

    @PutMapping("/device")
    @Operation(summary = "Update a record from the DEVICES table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND. There is no devicetype records with name).", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public Device updateDevice(@Valid @RequestBody DeviceUpdateDTO deviceUpdateDTO) {
        String deviceTypeName = deviceUpdateDTO.getDeviceType().toUpperCase();
        Devicetype devicetype = devicetypeRepository.findByName(deviceTypeName);
        if (devicetype == null) {
            throw new RecordNotFoundException("There is no devicetype records with name " + deviceTypeName);
        }
        Device device = new Device();
        device.setSystemname(deviceUpdateDTO.getSystemName().toUpperCase());
        device.setDevicetype(devicetype);
        device.setId(deviceUpdateDTO.getId());
        deviceRepository.save(device);
        return device;
    }

    @GetMapping("/device/{id}")
    @Operation(summary = "Get a record from the DEVICES table using its ID (DEVICES table primary key)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND. There is no records with id).", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public Device getDevice(@Parameter(description = "DEVICES ID (primary key of the table)") @PathVariable Integer id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isPresent()) {
            return deviceOptional.get();
        }
        throw new RecordNotFoundException("There is no device records with id " + id);
    }

    @GetMapping("/device")
    @Operation(summary = "Get all records from the DEVICES table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public List<Device> getDevice() {
        return deviceRepository.findAllByOrderBySystemnameAsc();
    }

    @DeleteMapping("/device/{id}")
    @Operation(summary = "Deletes a record from the DEVICES table using its ID (DEVICES table primary key)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND. There is no records with id).", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public void deleteDevice(@Parameter(description = "DEVICES ID (primary key of the table)") @PathVariable Integer id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();
            deviceRepository.delete(device);
        } else {
            throw new RecordNotFoundException("There is no device records with id " + id);
        }
    }
}
