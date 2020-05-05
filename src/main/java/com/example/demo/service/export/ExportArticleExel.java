package com.example.demo.service.export;

import com.example.demo.dto.ArticleDto;
import com.example.demo.service.ArticleService;

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
public class ExportArticleExel {

    @Autowired
    private ArticleService articleService;

    public void exportAll(OutputStream OutputStream) throws IOException {

        List<ArticleDto> articles = articleService.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Articles");

        Integer row = 0;

        Row firstHeaderRow = sheet.createRow(row);
        Cell firstCellLibelle = firstHeaderRow.createCell(0);
        Cell firstCellPrix = firstHeaderRow.createCell(1);
        firstCellLibelle.setCellValue("Libelle");
        firstCellPrix.setCellValue("Prix");

        for (ArticleDto articleDto : articles) {
            row = row + 1;
            Row headerRow = sheet.createRow(row);

            Cell cellLibelle = headerRow.createCell(0);
            Cell cellPrix = headerRow.createCell(1);
            cellLibelle.setCellValue(articleDto.getLibelle());
            cellPrix.setCellValue(articleDto.getPrix());
        }

        workbook.write(OutputStream);
        workbook.close();
    }

}
