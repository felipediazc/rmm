package com.ninjaone.rmm.service;

import com.ninjaone.rmm.db.entity.Customer;
import com.ninjaone.rmm.db.entity.Customerservicesdevice;
import com.ninjaone.rmm.db.repository.CustomerRepository;
import com.ninjaone.rmm.db.repository.CustomerservicesdeviceRepository;
import com.ninjaone.rmm.dto.TotalMonthlyCostDTO;
import com.ninjaone.rmm.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultMonthlyCostImpl implements MonthlyCosts {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerservicesdeviceRepository customerservicesdeviceRepository;

    @Override
    public TotalMonthlyCostDTO getTotalMonthlyCost(Integer customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            List<Customerservicesdevice> customerservicesdeviceList = customerservicesdeviceRepository.findAllByCustomer(customerOptional.get());
            double totalMonthlyCost = 0.0;
            TotalMonthlyCostDTO totalMonthlyCostDTO = new TotalMonthlyCostDTO();
            List<String> detailList = new ArrayList<>();
            for (Customerservicesdevice customerservicesdevice : customerservicesdeviceList) {
                String itemDescription;
                totalMonthlyCost += customerservicesdevice.getCost().getCost();
                if (customerservicesdevice.getCost().getDevice() != null) {
                    itemDescription = "Device: " + customerservicesdevice.getCost().getDevice().getSystemname();
                    itemDescription += " Type " + customerservicesdevice.getCost().getDevice().getDevicetype().getName();
                } else {
                    itemDescription = "Service: " + customerservicesdevice.getCost().getService().getServicename();
                    itemDescription += " For " + customerservicesdevice.getCost().getServicedevicetype().getName();
                }
                itemDescription += " $" + customerservicesdevice.getCost().getCost();
                detailList.add(itemDescription);
            }
            totalMonthlyCostDTO.setDetail(detailList);
            totalMonthlyCostDTO.setTotal(totalMonthlyCost);
            return totalMonthlyCostDTO;
        }
        throw new RecordNotFoundException("There is no customer records with id " + customerId);
    }
}
