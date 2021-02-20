package com.example.demo.service.export;

import com.example.demo.dto.ArticleDto;
import com.example.demo.service.ArticleService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportArticleExcelService {

    @Autowired
    private ArticleService articleService;

    private static String[] columns = {"Libelle", "Prix"};

    public void exportAll(OutputStream outputStream) throws IOException {

        // Create workbook
        Workbook workbook = new XSSFWorkbook();

        // Create sheet
        Sheet sheet = workbook.createSheet("Articles");

        // Create Font and Styling for header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLUE.getIndex());
        headerFont.setFontHeightInPoints((short) 12);

        //Create CellStyle with the Font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create Row
        Row headerRow = sheet.createRow(0);

        // Create Cells
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create rows and cells for Articles data
        int rowNum = 1;

        List<ArticleDto> allArticles = articleService.findAll();

        for (ArticleDto article : allArticles) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(article.getLibelle());
            row.createCell(1).setCellValue(article.getPrix());
        }

        // Resize all columns to fit content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write output to file
        workbook.write(outputStream);

        // Close workbook
        workbook.close();
    }
}
