package com.example.demo.dto;

import java.time.LocalDate;

/**
 * Classe permettant d'exposer des données au format JSON d'un client.
 */
public class ClientDto {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaisance;

    public ClientDto(Long id, String nom, String prenom, LocalDate dateNaisance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaisance = dateNaisance;
    }

    public Long getId() {
        return id;
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

    public LocalDate getDateNaisance() {
        return dateNaisance;
    }

    public void setDateNaisance(LocalDate dateNaisance) {
        this.dateNaisance = dateNaisance;
    }

    public int getAge() {
        LocalDate curr = LocalDate.now();
        LocalDate birth = this.dateNaisance;

        int age = curr.compareTo(birth);

        return age;
    }

}
