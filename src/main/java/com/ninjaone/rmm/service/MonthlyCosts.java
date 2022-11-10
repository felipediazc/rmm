package com.ninjaone.rmm.service;

import com.ninjaone.rmm.dto.TotalMonthlyCostDTO;

public interface MonthlyCosts {
    TotalMonthlyCostDTO getTotalMonthlyCost(Integer customerId);
}
