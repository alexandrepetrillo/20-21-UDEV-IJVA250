package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportArticleCSVService {

    @Autowired
    private ArticleRepository articleRepository;
    
    // Un printwriter est un objet dans lequel on peut écrire (ici on va écrire dans le résultat d'une requête http).
    public void exportAll(PrintWriter writer) {

        //génération d'un fichier CSV exemples avec 2 colonnes et 4 lignes
    	writer.println("Libelle;Prix");
        
        /*
        @Autowired
    	private ArticleRepository articleRepository;
    
        for (int i=0; i< articleService.findAll().size(); i++) {
        	writer.println(articleService.findAll().get(i).libelle +";" + articleService.findAll().get(i).prix);
        };
        */
        
        List<Article> allArticles = articleRepository.findAll();
        for (Article article : allArticles) {
        	writer.println(article.getLibelle() +";" + article.getPrix());
        }
      
    }

}
