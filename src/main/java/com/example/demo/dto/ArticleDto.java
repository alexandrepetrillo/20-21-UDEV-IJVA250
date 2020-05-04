package com.example.demo.dto;

/**
 * Classe permettant d'exposer des données au format JSON d'un article.
 */
public class ArticleDto {
    private Long id;
    private String libelle;
    private double prix;
    
    public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	

    public ArticleDto(Long id, String libelle, double prix) {
        this.id = id;
        this.libelle = libelle;
        this.prix = prix;
    }
}
