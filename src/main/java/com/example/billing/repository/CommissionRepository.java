package com.example.billing.repository;


import com.example.billing.entity.Commission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommissionRepository extends JpaRepository<Commission, Integer> {

}
