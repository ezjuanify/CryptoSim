package com.dotjuan89.aquariuxtest.repository;

import com.dotjuan89.aquariuxtest.entity.UsersEntity;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {
}
