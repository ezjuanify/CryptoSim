package com.dotjuan89.aquariuxtest.repository;

import com.dotjuan89.aquariuxtest.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Integer> {
    PriceEntity findTopByOrderByIdDesc();
}
