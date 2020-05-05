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
    	List<ArticleDto> articles = articleService.findAll();

        //génération d'un fichier CSV articles
        writer.println("Libelle;Prix");
        for (ArticleDto article : articles) {
    		writer.println(article.libelle+";"+article.prix);
    	}
       
    }

}
