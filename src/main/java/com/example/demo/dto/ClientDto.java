package com.example.demo.dto;

import java.time.LocalDate;

/**
 * Classe permettant d'exposer des données au format JSON d'un client.
 */
public class ClientDto {
    public Long id;
    public String nom;
    public String prenom;
    public LocalDate dateNaisance;

    public ClientDto(Long id, String nom, String prenom, LocalDate dateNaisance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaisance = dateNaisance;
    }

    public LocalDate getDateNaissance() {
       return dateNaisance;
    }
}
