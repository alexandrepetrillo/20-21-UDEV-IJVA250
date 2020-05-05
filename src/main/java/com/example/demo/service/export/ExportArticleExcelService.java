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
        cellLibelle.setCellValue("Libellé");
        Cell cellPrix = headerRow.createCell(1);
        cellPrix.setCellValue("Prix");

        for(int i = 0; i < allArticles.size(); i++) {
            Row row = sheet.createRow(i +1);
            Cell libelle = row.createCell(0);
            libelle.setCellValue(allArticles.get(i).getLibelle());
            Cell prix = row.createCell(1);
            prix.setCellValue(allArticles.get(i).getPrix());
        }

        workbook.write(outputStream);
        workbook.close();
    }
}
