package com.bkopec.annonces.repository;

import com.bkopec.annonces.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {
    Optional<Departement> findByCode(String code);
}