package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
public class ExportArticleXLSX {
	@Autowired
    private ArticleService articleService;

    public void exportAll(OutputStream outputStream) throws IOException {

    	Workbook workbook = new XSSFWorkbook();
    	Sheet sheet = workbook.createSheet("Articles");
    	
    	Row headerRow = sheet.createRow(0);
    	Cell cell = headerRow.createCell(0);
    	cell.setCellValue("Libelle");
    	Cell cell2 = headerRow.createCell(1);
    	cell2.setCellValue("Prix");
    	
    	int i = 1;
    	
    	List<ArticleDto> artList = articleService.findAll();
    	for (ArticleDto articleDto : artList) {
			//articleDto.libelle+";"+articleDto.prix);
    		Row headerRowN = sheet.createRow(i);
    		Cell cellL = headerRowN.createCell(0);
        	cellL.setCellValue(articleDto.libelle);
        	Cell cellP = headerRowN.createCell(1);
        	cellP.setCellValue(articleDto.prix);
        	i++;
		}
    	sheet.autoSizeColumn(0);
    	sheet.autoSizeColumn(1);
    	
    	workbook.write(outputStream);
    	workbook.close();
    	
    }
}
