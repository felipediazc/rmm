package com.ninjaone.rmm.db.repository;

import com.ninjaone.rmm.db.entity.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, Integer> {
    List<Device> findAllByOrderBySystemnameAsc();
    Device findBySystemname(String name);
}