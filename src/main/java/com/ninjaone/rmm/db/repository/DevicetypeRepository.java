package com.ninjaone.rmm.db.repository;

import com.ninjaone.rmm.db.entity.Devicetype;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DevicetypeRepository extends CrudRepository<Devicetype, Integer> {
    List<Devicetype> findAllByOrderByNameAsc();
    Devicetype findByName(String name);
}