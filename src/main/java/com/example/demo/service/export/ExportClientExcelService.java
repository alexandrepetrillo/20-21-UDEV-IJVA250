package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExportClientExcelService {

    @Autowired
    private ClientRepository clientRepository;

    public void exportAll(OutputStream outputStream) throws IOException {
        List<Client> allClients = clientRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clients");
        Row headerRow = sheet.createRow(0);
        Cell cellNom = headerRow.createCell(0);
        cellNom.setCellValue("Nom");
        Cell cellPrenom = headerRow.createCell(1);
        cellPrenom.setCellValue("Prénom");
        Cell cellAge = headerRow.createCell(2);
        cellAge.setCellValue("Âge");


        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.PINK.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        this.borderThickBlue(headerCellStyle);

        cellNom.setCellStyle(headerCellStyle);
        cellPrenom.setCellStyle(headerCellStyle);
        cellAge.setCellStyle(headerCellStyle);

        CellStyle rowStyle = workbook.createCellStyle();
        this.borderThickBlue(rowStyle);


        for(int i = 0; i < allClients.size(); i++) {
            Row row = sheet.createRow(i +1);
            Cell nom = row.createCell(0);
            nom.setCellValue(allClients.get(i).getNom());
            Cell prenom = row.createCell(1);
            prenom.setCellValue(allClients.get(i).getPrenom());
            Cell age = row.createCell(2);
            age.setCellValue(LocalDate.now().getYear() - allClients.get(i).getDateNaissance().getYear() + " ans");

            nom.setCellStyle(rowStyle);
            prenom.setCellStyle(rowStyle);
            age.setCellStyle(rowStyle);
        }


        workbook.write(outputStream);
        workbook.close();
    }

    private void borderThickBlue(CellStyle cellStyle) {
        cellStyle.setBorderTop(BorderStyle.THICK);
        cellStyle.setBorderBottom(BorderStyle.THICK);
        cellStyle.setBorderLeft(BorderStyle.THICK);
        cellStyle.setBorderRight(BorderStyle.THICK);
        cellStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
        cellStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
    }
}
