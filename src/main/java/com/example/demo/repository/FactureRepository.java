package com.example.demo.repository;

import com.example.demo.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Repository permettant l'interraction avec la base de données pour les factures.
 */
@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
}

