package com.ninjaone.rmm.db.repository;

import com.ninjaone.rmm.db.entity.Cost;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CostRepository extends CrudRepository<Cost, Integer> {
    List<Cost> findAllByOrderByIdAsc();
}