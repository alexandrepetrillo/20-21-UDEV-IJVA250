package com.example.demo.service.export;

import com.example.demo.dto.ArticleDto;
import com.example.demo.service.ArticleService;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.awt.List;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@Service
public class ExportArticleXLSXService {

    @Autowired
    private ArticleService articleService;

    public void exportAll(OutputStream fileOutputStream) throws IOException {
    	java.util.List<ArticleDto> toutLesArticle = articleService.findAll();
    	// je cree manuellement les celule 0 et 1 a cause de la nature de notre DTO, 
    	Workbook workbook = new XSSFWorkbook();
    	Sheet sheet = workbook.createSheet("Articles");
    	Row headerRow = sheet.createRow(0);
		Cell cellLibelleName = headerRow.createCell(0);
		cellLibelleName.setCellValue("Libelle");
		Cell cellPrixName = headerRow.createCell(1);
		cellPrixName.setCellValue("Prix");
    	int i=1;
    	for(ArticleDto article:toutLesArticle) {
    		headerRow = sheet.createRow(i);
    		Cell cellLibelle = headerRow.createCell(0);
    		cellLibelle.setCellValue(article.getLibelle());
    		Cell cellPrix = headerRow.createCell(1);
    		cellPrix.setCellValue(article.getPrix());
        	i++;
    	}
    	
    	workbook.write(fileOutputStream);
    	workbook.close();
       }

}
