package com.ninjaone.rmm.db.repository;

import com.ninjaone.rmm.db.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}