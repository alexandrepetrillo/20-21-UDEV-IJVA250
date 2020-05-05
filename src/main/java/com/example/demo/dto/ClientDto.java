package com.example.demo.dto;

import java.time.LocalDate;
import java.time.Period;

/**
 * Classe permettant d'exposer des données au format JSON d'un client.
 */
public class ClientDto {
    public Long id;
    public String nom;
    public String prenom;
    public LocalDate dateNaissance;

    public ClientDto(Long id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
    
    public ClientDto(Long id, String nom, String prenom, LocalDate dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }
    
    public int getAge() {
    	return Period.between(this.dateNaissance, LocalDate.now()).getYears();
    }
}
