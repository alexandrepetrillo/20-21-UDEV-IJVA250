package com.example.demo.dto;

import java.time.LocalDate;

/**
 * Classe permettant d'exposer des données au format JSON d'un client.
 */
public class ClientDto {
    public Long id;
    public String nom;
    public String prenom;
    public LocalDate dateNaissance;
    
    public ClientDto(Long id, String nom, String prenom, LocalDate dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

	public LocalDate getDateNaissance() {
		// TODO Auto-generated method stub
		return this.dateNaissance;
	}

	public String getNom() {
		// TODO Auto-generated method stub
		return this.nom;
	}

	public String getPrenom() {
		// TODO Auto-generated method stub
		return this.prenom;
	}
}
