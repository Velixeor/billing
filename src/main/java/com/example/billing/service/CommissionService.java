package com.example.billing.service;


import com.example.billing.dto.CommissionDTO;
import com.example.billing.entity.Commission;
import com.example.billing.repository.CommissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommissionService {
    private final CommissionRepository commissionRepository;

    public CommissionDTO createCommission(CommissionDTO commissionDTO) {
        log.info("Start commission creation");

        Commission commission = commissionDTO.toEntity();

        Commission savedCommission = commissionRepository.save(commission);
        log.info("Commission created successfully: {}", commissionDTO);

        return CommissionDTO.fromEntity(savedCommission);
    }
    public boolean processCommission(int id, int fromWhom, int toWhom, double amount, String currency) {
        log.info("Processing commission with ID: {}, from: {}, to: {}, amount: {}, currency: {}",
                id, fromWhom, toWhom, amount, currency);

        try {

            CommissionDTO commissionDTO = new CommissionDTO(id, fromWhom, toWhom, BigDecimal.valueOf(amount), currency);


            createCommission(commissionDTO);

            log.info("Commission processed successfully");

            return true;
        } catch (Exception e) {
            log.error("Error processing commission: {}", e.getMessage(), e);
            return false;
        }
    }
}
