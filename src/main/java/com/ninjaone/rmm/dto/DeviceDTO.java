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
public class DeviceDTO {

    @Size(max = 60)
    @NotNull(message = "systemName is mandatory")
    @ApiModelProperty(notes = "Device Name (Must be an unique name into the DEVICES table)", example = "windows 11 home", required = true)
    private String systemName;
    @Size(max = 30)
    @NotNull(message = "deviceType is mandatory")
    @ApiModelProperty(notes = "Device Type Name (must exist into the DEVICETYPES table)", example = "windows", required = true)
    private String deviceType;
}
