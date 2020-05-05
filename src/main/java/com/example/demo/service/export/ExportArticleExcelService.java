package com.example.demo.service.export;

import com.example.demo.dto.ArticleDto;
import com.example.demo.service.ArticleService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExportArticleExcelService {

    @Autowired
    private ArticleService articleService;

    public void exportAll(OutputStream outputStream) throws IOException {

        //Création des headers
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Articles");
        Row headerRow = sheet.createRow(0);
        Cell cellTitleLib = headerRow.createCell(0);
        cellTitleLib.setCellValue("Libelle");
        Cell cellTitlePrix = headerRow.createCell(1);
        cellTitlePrix.setCellValue("Prix");

        List<ArticleDto> listes =  articleService.findAll();

        for (int i=0; i< listes.size(); i++){
            headerRow = sheet.createRow(i+1);
            Cell cellTitleLibelle = headerRow.createCell(0);
            cellTitleLibelle.setCellValue(listes.get(i).libelle);
            Cell cellPrix = headerRow.createCell(1);
            cellPrix.setCellValue(listes.get(i).prix);
        }
        sheet.autoSizeColumn(0);
        workbook.write(outputStream);
        workbook.close();
    }
}


