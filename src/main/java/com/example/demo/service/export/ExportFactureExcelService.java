package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ExportFactureExcelService {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private ClientRepository clientRepository;

    public void exportAllFactureXlsx(OutputStream outputStream) throws IOException {

        List<Facture> factures= factureRepository.findAll();
        Facture template;

        List<Client> clients= clientRepository.findAll();
        Client template2;

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Factures");

        sheet.setColumnWidth(0, 8000);
        sheet.setColumnWidth(1, 2000);

        Row headerRow = sheet.createRow(0);
        Row headerRow2 = sheet.createRow(0);
        Row headerRow3 = sheet.createRow(0);
        for (int i = 0; i < clients.size(); i++){

            // Create first HEADER colomn
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Nom");

            // Create second HEADER colomn
            headerCell = headerRow2.createCell(0);
            headerCell.setCellValue("Prenom");

            // Create second HEADER colomn
            headerCell = headerRow3.createCell(0);
            headerCell.setCellValue("Date de Naissance");

            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            template2 = clients.get(i);

            //Create SECOND ROW .. after header row
            Row row = sheet.createRow(i);

            // Create second CELL & Add LIBELLE in to cell
            Cell cell = row.createCell(1);
            cell.setCellValue(template2.getNom());
            cell.setCellStyle(style);

            // Create second CELL & Add PRIX in to cell
            cell = row.createCell(1);
            cell.setCellValue(template2.getPrenom());
            cell.setCellStyle(style);


            // Create second CELL & Add PRIX in to cell
            cell = row.createCell(1);
            cell.setCellValue(String.valueOf(template2.getDateNaissance()));
            cell.setCellStyle(style);

        }


        workbook.write(outputStream);
        workbook.close();

    }
}
