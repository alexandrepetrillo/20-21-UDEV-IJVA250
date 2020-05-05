package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.service.ArticleService;

@Service
public class ExportArticleXlsx {

	@Autowired
	private ArticleService articleService;
	
	public void exportAll(OutputStream outputStream) throws IOException {
		
		List<ArticleDto> allArticles = articleService.findAll();	
		
		Workbook workbook = new XSSFWorkbook();
		
		Sheet sheet = workbook.createSheet("Articles");
		sheet.setColumnWidth(0, 10000);
		sheet.setColumnWidth(1, 6000);
		
		Row headerRow = sheet.createRow(0);
		
		Cell cell = headerRow.createCell(0);
		
		cell.setCellValue("Libellé");
				
		cell = headerRow.createCell(1);
		
		cell.setCellValue("Prix");
		 
		for(int i = 0; i < allArticles.size();i++) {
		  
			 headerRow = sheet.createRow(i+1);
			 
			 cell = headerRow.createCell(0); 
			 cell.setCellValue(allArticles.get(i).libelle);

			 cell = headerRow.createCell(1); 
			 cell.setCellValue(allArticles.get(i).prix); 
			  	  
		  }
		 
		workbook.write(outputStream); //Permet d'écrire dans un fichier qui ne prend pas du texte (xlsx,pdf etc) prennent du byte
		workbook.close();
	}
}
