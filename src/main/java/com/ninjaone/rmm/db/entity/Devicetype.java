package com.ninjaone.rmm.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "DEVICETYPES",
        indexes = {
        @Index(name = "devicetype_unique_name_idx", columnList = "name", unique = true)})
public class Devicetype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @ApiModelProperty(value = "devicetype id (primary key of the DEVICETYPES table)", example = "1")
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 1048576)
    @ApiModelProperty(value = "devicetype name", example = "MAC")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}