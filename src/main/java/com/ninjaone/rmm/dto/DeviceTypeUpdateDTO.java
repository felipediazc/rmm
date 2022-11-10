package com.ninjaone.rmm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTypeUpdateDTO extends DeviceTypeDTO {

    @NotNull(message = "Device Type ID is mandatory")
    @ApiModelProperty(notes = "Device Type ID (must exist into the DEVICETYPES table)", example = "1", required = true)
    Integer id;
}
