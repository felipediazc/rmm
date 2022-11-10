package com.ninjaone.rmm.db.repository;

import com.ninjaone.rmm.db.entity.Customer;
import com.ninjaone.rmm.db.entity.Customerservicesdevice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerservicesdeviceRepository extends CrudRepository<Customerservicesdevice, Integer> {
    List<Customerservicesdevice> findAllByOrderByIdAsc();
    List<Customerservicesdevice> findAllByCustomer(Customer customer);
}