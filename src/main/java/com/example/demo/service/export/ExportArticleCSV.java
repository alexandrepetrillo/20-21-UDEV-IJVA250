package com.example.demo.service.export;

import com.example.demo.dto.ArticleDto;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportArticleCSV {

    @Autowired
    private ArticleService articleService;

    public void exportAll(PrintWriter writer) {

        //génération d'un fichier CSV exemples avec 2 colonnes et 4 lignes
        writer.println("Libelle;Prix");

        List<ArticleDto> listAllArticle = this.articleService.findAll();
        for (ArticleDto articleDto : listAllArticle) {
            writer.println(articleDto.libelle+";"+articleDto.prix);
		}

    }

}
