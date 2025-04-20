package io.juanez.cryptosim.repository;

import io.juanez.cryptosim.entity.PricesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricesRepository extends JpaRepository<PricesEntity, Integer> {
    PricesEntity findTopByOrderByIdDesc();
}
