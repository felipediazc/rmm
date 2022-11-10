package com.ninjaone.rmm.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "COSTS",
        indexes = {
                @Index(name = "costs_unique_name_idx1", columnList = "service, servicedevicetype", unique = true),
                @Index(name = "costs_unique_name_idx2", columnList = "device", unique = true)})
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @ApiModelProperty(value = "cost id (primary key of the COSTS table)", example = "1")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "SERVICE")
    private Service service;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERVICEDEVICETYPE")
    private Devicetype servicedevicetype;

    @Column(name = "COST", nullable = false)
    @ApiModelProperty(value = "cost", example = "14.0")
    private Double cost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEVICE")
    private Device device;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Devicetype getServicedevicetype() {
        return servicedevicetype;
    }

    public void setServicedevicetype(Devicetype servicedevicetype) {
        this.servicedevicetype = servicedevicetype;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

}