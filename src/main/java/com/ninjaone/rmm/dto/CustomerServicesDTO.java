package com.ninjaone.rmm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerServicesDTO {
    @NotNull(message = "customer is mandatory")
    @ApiModelProperty(value = "customers id (Primary key of the CUSTOMERS table)", example = "1", required = true)
    private Integer customer;
    @NotNull(message = "cost is mandatory")
    @ApiModelProperty(value = "costs id (Primary key of the COSTS table)", example = "1", required = true)
    private Integer cost;
}
