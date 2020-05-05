package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExportArticleExcelService<ExcelTemplate> {

    @Autowired
    private ArticleRepository articleRepository;

    public void exportAllArticleXlsx(OutputStream outputStream) throws IOException {

        List<Article> articles= articleRepository.findAll();
        Article template;

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Articles");

        sheet.setColumnWidth(0, 8000);
        sheet.setColumnWidth(1, 2000);

        Row headerRow = sheet.createRow(0);

       for (int i = 0; i < articles.size(); i++){

           // Create first HEADER colomn
           Cell headerCell = headerRow.createCell(0);
           headerCell.setCellValue("Libelle");

           // Create second HEADER colomn
           headerCell = headerRow.createCell(1);
           headerCell.setCellValue("Prix");

           CellStyle style = workbook.createCellStyle();
           style.setWrapText(true);
           template = articles.get(i);

           //Create SECOND ROW .. after header row
           Row row = sheet.createRow(i+1);

           // Create second CELL & Add LIBELLE in to cell
           Cell cell = row.createCell(0);
           cell.setCellValue(template.getLibelle());
           cell.setCellStyle(style);

           // Create second CELL & Add PRIX in to cell
           cell = row.createCell(1);
           cell.setCellValue(template.getPrix());
           cell.setCellStyle(style);

        }


        workbook.write(outputStream);
        workbook.close();

    }
}

