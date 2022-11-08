package com.dotjuan89.aquariuxtest.repository;

import com.dotjuan89.aquariuxtest.entity.PricesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricesRepository extends JpaRepository<PricesEntity, Integer> {
    PricesEntity findTopByOrderByIdDesc();
}
