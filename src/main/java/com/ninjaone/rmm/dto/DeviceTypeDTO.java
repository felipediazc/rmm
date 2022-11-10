package com.ninjaone.rmm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTypeDTO {

    @Size(max = 30)
    @NotNull(message = "Name is mandatory")
    @ApiModelProperty(notes = "Device Type Name (Must be an unique name into the DEVICETYPES table)", example = "windows", required = true)
    private String name;
}
