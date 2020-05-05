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

    public boolean hasChanged(ClientDto client) {
        if(this.id==client.id) {
            return true;
        }
        return false;
    }
}
