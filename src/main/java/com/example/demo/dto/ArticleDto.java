package com.example.demo.dto;

/**
 * Classe permettant d'exposer des données au format JSON d'un article.
 */
public class ArticleDto {
    public Long id;
    public String libelle;
    public double prix;
    public String description;

    public ArticleDto(Long id, String libelle, double prix, String description) {
        this.id = id;
        this.libelle = libelle;
        this.prix = prix;
        this.description = description;
    }
}
