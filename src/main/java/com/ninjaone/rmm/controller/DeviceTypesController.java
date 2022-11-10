package com.ninjaone.rmm.controller;

import com.ninjaone.rmm.db.entity.Devicetype;
import com.ninjaone.rmm.db.repository.DevicetypeRepository;
import com.ninjaone.rmm.dto.DeviceTypeDTO;
import com.ninjaone.rmm.dto.DeviceTypeUpdateDTO;
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
public class DeviceTypesController {

    @Autowired
    private DevicetypeRepository devicetypeRepository;

    @PostMapping("/devicetype")
    @Operation(summary = "Add a record to the DEVICETYPES table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public Devicetype addDeviceType(@Valid @RequestBody DeviceTypeDTO deviceTypeDTO) {
        Devicetype devicetype = new Devicetype();
        devicetype.setName(deviceTypeDTO.getName().toUpperCase());
        devicetypeRepository.save(devicetype);
        return devicetype;
    }

    @PutMapping("/devicetype")
    @Operation(summary = "Update a record from the DEVICETYPES table using its ID (table primary key)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public Devicetype updateDeviceType(@Valid @RequestBody DeviceTypeUpdateDTO deviceTypeUpdateDTO) {
        Devicetype devicetype = new Devicetype();
        devicetype.setName(deviceTypeUpdateDTO.getName().toUpperCase());
        devicetype.setId(deviceTypeUpdateDTO.getId());
        devicetypeRepository.save(devicetype);
        return devicetype;
    }

    @GetMapping("/devicetype/{id}")
    @Operation(summary = "Get a record from the DEVICETYPES table using its ID (DEVICETYPES table primary key)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Devicetype.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND. There is no records with id).", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public Devicetype getDeviceType(@Parameter(description = "DEVICETYPES ID (primary key of the table)") @PathVariable Integer id) {
        Optional<Devicetype> deviceTypeOptional = devicetypeRepository.findById(id);
        if (deviceTypeOptional.isPresent()) {
            return deviceTypeOptional.get();
        }
        throw new RecordNotFoundException("There is no devicetype records with id " + id);
    }

    @GetMapping("/devicetype")
    @Operation(summary = "Get all records from the DEVICETYPES table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public List<Devicetype> getDeviceType() {
        return  devicetypeRepository.findAllByOrderByNameAsc();
    }

}
