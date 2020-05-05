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

    public ClientDto(Long id, String nom, String prenom, LocalDate dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }
    
//    /*méthode calcul âge*/
//    public final Integer calculAge() {
//        return LocalDate.now().getYear() - dateNaissance.getYear();
//    }
    
//    /*méthode calcul âge*/
//    public final Integer calculAge2(LocalDate dateJour, LocalDate dateNaissance) {
//        return Period.between(dateJour, dateNaissance).getYears();
//    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
}
