package com.example.demo.repository;

import com.example.demo.model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComandaRepository extends JpaRepository <Comanda, Long> {
}
