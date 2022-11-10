package com.ninjaone.rmm.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "SERVICES",
        indexes = {
                @Index(name = "service_unique_name_idx", columnList = "servicename", unique = true)})
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @ApiModelProperty(value = "services id (primary key of the SERVICES table)", example = "1")
    private Integer id;

    @Column(name = "SERVICENAME", nullable = false, length = 1048576)
    @ApiModelProperty(value = "services name", example = "BACKUP")
    private String servicename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

}