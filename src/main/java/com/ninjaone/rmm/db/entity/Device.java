package com.ninjaone.rmm.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "DEVICES",
        indexes = {
        @Index(name = "device_unique_name_idx", columnList = "systemname", unique = true)})
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @ApiModelProperty(value = "device id (primary key of the DEVICES table)", example = "1")
    private Integer id;

    @Column(name = "SYSTEMNAME", nullable = false, length = 1048576)
    @ApiModelProperty(value = "device name", example = "IMAC 5K")
    private String systemname;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "DEVICETYPE", nullable = false)
    private Devicetype devicetype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemname() {
        return systemname;
    }

    public void setSystemname(String systemname) {
        this.systemname = systemname;
    }

    public Devicetype getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(Devicetype devicetype) {
        this.devicetype = devicetype;
    }

}