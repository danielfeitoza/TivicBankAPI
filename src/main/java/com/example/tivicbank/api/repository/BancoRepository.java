package com.example.tivicbank.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tivicbank.api.model.Banco;
@Repository
public interface BancoRepository extends JpaRepository<Banco, Long>{

}
