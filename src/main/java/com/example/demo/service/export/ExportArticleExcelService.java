package com.example.demo.service.export;

import com.example.demo.dto.ArticleDto;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExportArticleExcelService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleService articleService;

    public void exportAll(OutputStream outputStream) throws IOException {
        List<Article> allArticles = articleRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Articles");

        Row headerRow = sheet.createRow(0);
        
        // Styles des cellules: 
        CellStyle styleGreen = workbook.createCellStyle();
        styleGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        styleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
        
        CellStyle styleBlue = workbook.createCellStyle();
        styleBlue.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        styleBlue.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
        
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        styleGreen.setFont(font);
        styleBlue.setFont(font);
        
        
        //LIBELLES
        Cell cellLibelleNom = headerRow.createCell(0);
        Cell cellLibellePrenom = headerRow.createCell(1);
        cellLibelleNom.setCellValue("Libellé");
        cellLibellePrenom.setCellValue("Prix");
        cellLibelleNom.setCellStyle(styleGreen);
        cellLibellePrenom.setCellStyle(styleBlue);
        
        
        
        List<ArticleDto> listAllArticle = this.articleService.findAll();
        
        for (int i =0; i<listAllArticle.size(); i++) {
        	
        	ArticleDto articleDto = listAllArticle.get(i);
        	
            Row newRow = sheet.createRow(i+1);
            
            Cell firstCell = newRow.createCell(0);
            firstCell.setCellStyle(styleGreen);
            firstCell.setCellValue(articleDto.libelle);
            
            Cell secondCell =newRow.createCell(1);
            secondCell.setCellValue(articleDto.prix);
            secondCell.setCellStyle(styleBlue);
		}
        

        workbook.write(outputStream);
        workbook.close();
    }

}