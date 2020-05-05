package com.example.demo.dto;

import java.time.LocalDate;

/**
 * Classe permettant d'exposer des données au format JSON d'un client.
 */
public class ClientDto {
	private Long id;
	private String nom;
	private String prenom;
	private LocalDate dateNaissance;

	public ClientDto(Long id, String nom, String prenom) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}

	public ClientDto(Long id, String nom, String prenom, LocalDate dateNaissance) {
		this(id, nom, prenom);
		this.dateNaissance = dateNaissance;
	}

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

	public Integer getAge() {
		Integer anneNaissance = dateNaissance.getYear();
		Integer anneEnCours = LocalDate.now().getYear();
		return anneEnCours - anneNaissance;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

}
