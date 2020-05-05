package com.example.demo.service.export;

import com.example.demo.dto.ArticleDto;
import com.example.demo.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportArticleCSVService {

    @Autowired
    private ArticleService articleService;

    public void exportAll(PrintWriter writer) {

        //génération d'un fichier CSV exemples avec 2 colonnes et 4 lignes
        writer.println("Libelle;Prix");
        
        List<ArticleDto> listeArticles = articleService.findAll();
        for (ArticleDto article : listeArticles) {
        	writer.println(article.libelle + ";" + article.prix);
        }
    }
    
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    public void exportAll(PrintWriter writer) {
//        //génération d'un fichier CSV exemples avec 2 colonnes et 4 lignes
//        writer.println("Libelle;Prix");
//
//        List<Article> allArticles = articleRepository.findAll();
//        for (Article article : allArticles) {
//            writer.println(article.getLibelle() + ";" + article.getPrix());
//        }
//    }

}
