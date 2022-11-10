package com.ninjaone.rmm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceUpdateDTO extends DeviceDTO {
    @NotNull(message = "Device ID is mandatory")
    @ApiModelProperty(value = "device id (primary key of the DEVICES table)", example = "1", required = true)
    private Integer id;
}
