package com.ninjaone.rmm.db.repository;

import com.ninjaone.rmm.db.entity.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceRepository extends CrudRepository<Service, Integer> {

    List<Service> findAllByOrderByServicenameAsc();
    Service findByServicename(String name);
}