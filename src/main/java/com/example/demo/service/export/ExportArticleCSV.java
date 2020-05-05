package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportArticleCSV {

    @Autowired
    private ArticleRepository articleRepository;

    public void exportAll(PrintWriter writer) {
        // Get a list of articles
        List<Article> articles= articleRepository.findAll();
        writer.println("Libelle;Prix");

        // Filled the cells with LIBELLE & PRIX
        for (Article article : articles){
          writer.println(article.getLibelle() + ";" + article.getPrix());
        }

    }

}
