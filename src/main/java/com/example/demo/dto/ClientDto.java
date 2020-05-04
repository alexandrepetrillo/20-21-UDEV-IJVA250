package com.example.demo.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe permettant d'exposer des données au format JSON d'un client.
 */
public class ClientDto {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateDeNaissance;
    
    
    
    public ClientDto(Long id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public ClientDto(Long id, String nom, String prenom,LocalDate dateDeNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance=dateDeNaissance;
    }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateDeNaissance() {
		return dateDeNaissance;
	}

	public void setDateDeNaissance(LocalDate dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
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



	
	public boolean equals(ClientDto client) {
		if(this.id==client.getId())
			return true;
		return false;
		
	}



}
