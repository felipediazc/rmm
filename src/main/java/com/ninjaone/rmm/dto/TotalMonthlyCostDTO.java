package com.ninjaone.rmm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalMonthlyCostDTO {
    @ApiModelProperty(notes = "total monthly cost for devices and services)", example = "11")
    private Double total = 0.0;
    @ApiModelProperty(notes = "detailed list of devices and services)", example = "[\"Device: WINDOWS 10 HOME Type WINDOWS $4.0\",\"Service: ANTIVIRUS For MAC $7.0\"]")
    List<String> detail;
}
