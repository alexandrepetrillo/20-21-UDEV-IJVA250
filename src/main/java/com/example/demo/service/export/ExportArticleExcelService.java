package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.apache.poi.ss.usermodel.Cell;
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

    public void exportAll(OutputStream outputStream) throws IOException {
        List<Article> allArticles = articleRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Articles");

        Row headerRow = sheet.createRow(0);
        Cell cellLibelle = headerRow.createCell(0);
        Cell cellPrix = headerRow.createCell(1);
        cellLibelle.setCellValue("Libellé");
        cellPrix.setCellValue("Prix");
        
        
        Integer rowNumber = 1;
        
        for (Article article : allArticles) {
        	
        	Row dataRow = sheet.createRow(rowNumber);
        	Cell cellDataLibelle = dataRow.createCell(0);
            Cell cellDataPrix = dataRow.createCell(1);
            cellDataLibelle.setCellValue(article.getLibelle());
            cellDataPrix.setCellValue(article.getPrix());
        	rowNumber ++;
        }

        workbook.write(outputStream);
        workbook.close();
    }

}
