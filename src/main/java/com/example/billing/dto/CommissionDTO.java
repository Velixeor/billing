package com.example.billing.dto;


import com.example.billing.entity.Commission;
import lombok.Builder;

import java.math.BigDecimal;


@Builder
public record CommissionDTO(Integer id, Integer fromWhom, Integer toWhom, BigDecimal amount, String currency) {

 
    public static CommissionDTO fromEntity(Commission commission) {
        return CommissionDTO.builder()
                .id(commission.getId())
                .fromWhom(commission.getFromWhom())
                .toWhom(commission.getToWhom())
                .amount(commission.getAmount())
                .currency(commission.getCurrency())
                .build();
    }


    public Commission toEntity() {
        Commission commission = new Commission();
        commission.setId(this.id);
        commission.setFromWhom(this.fromWhom);
        commission.setToWhom(this.toWhom);
        commission.setAmount(this.amount);
        commission.setCurrency(this.currency);
        return commission;
    }
}
