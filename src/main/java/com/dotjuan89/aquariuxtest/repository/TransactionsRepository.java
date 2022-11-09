package com.dotjuan89.aquariuxtest.repository;

import com.dotjuan89.aquariuxtest.entity.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionsEntity, Integer> {
    List<TransactionsEntity> findTransactionsByUsersId(int id);
}
