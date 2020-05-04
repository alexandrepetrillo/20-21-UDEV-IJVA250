package com.example.demo.service.export;

import com.example.demo.dto.ArticleDto;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.awt.List;
import java.io.PrintWriter;

@Service
public class ExportArticleCSVService {

    @Autowired
    private ArticleService articleService;

    public void exportAll(PrintWriter writer) {
    	java.util.List<ArticleDto> toutLesArticle = articleService.findAll();
        writer.println("Libelle;Prix");
    	toutLesArticle.stream().forEach(n ->writer.println(n.getLibelle()+";"+n.getPrix()));
        

            }

}
