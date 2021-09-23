package com.example.tivicbank.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tivicbank.api.model.Deposito;

@Repository
public interface DepositoRepository extends JpaRepository<Deposito, Long>{

}
