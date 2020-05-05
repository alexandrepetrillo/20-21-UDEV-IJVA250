package com.example.demo.service.export;

import com.example.demo.dto.ArticleDto;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportArticleCSV {

    @Autowired
    private ArticleService articleService;

    public void exportAll(PrintWriter writer) {

    	writer.println("Libelle;Prix");
    	List<ArticleDto> artList = articleService.findAll();
    	for (ArticleDto articleDto : artList) {
			writer.println(articleDto.libelle+";"+articleDto.prix);
		}
        
    	//articleService.findAll().stream().forEach(art -> writer.println(art.libelle + ";" + art.prix));
    	
    }

}
