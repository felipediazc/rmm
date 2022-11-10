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
public class CostDTO {
    @Size(max = 60)
    @ApiModelProperty(value = "Service name (must exist in the SERVICES table)", example = "BACKUP")
    private String service;
    @Size(max = 30)
    @ApiModelProperty(value = "Service type name (must exist in the SERVICETYPES table)", example = "WINDOWS")
    private String serviceDeviceType;
    @NotNull(message = "cost is mandatory")
    @ApiModelProperty(value = "Service/Device Cost", example = "14.0", required = true)
    private Double cost;
    @Size(max = 60)
    @ApiModelProperty(value = "Device name (must exist in the DEVICES table)", example = "WINDOWS 10 HOME")
    private String device;
}
