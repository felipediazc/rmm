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
public class ServiceDTO {
    @Size(max = 60)
    @NotNull(message = "Name is mandatory")
    @ApiModelProperty(notes = "Service name (Must be an unique name in the SERVICES table)", example = "windows", required = true)
    private String servicename;
}
