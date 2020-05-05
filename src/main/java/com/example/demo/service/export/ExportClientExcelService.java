package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;
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
    ClientService clientService;

    public void exportAll(OutputStream outputStream) throws IOException {

        List<ClientDto>  listes = clientService.findAllClients();
        BorderStyle styleBorder = BorderStyle.THICK;
        short colorBorder = IndexedColors.BLUE.getIndex();

        Workbook workbook = new XSSFWorkbook();
        //Changement de la font et de la couleur
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.PINK.getIndex());

        //Application de bordures pour les headers
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderTop(styleBorder);
        headerCellStyle.setBorderBottom(styleBorder);
        headerCellStyle.setBorderLeft(styleBorder);
        headerCellStyle.setBorderRight(styleBorder);
        headerCellStyle.setRightBorderColor(colorBorder);
        headerCellStyle.setBottomBorderColor(colorBorder);
        headerCellStyle.setLeftBorderColor(colorBorder);
        headerCellStyle.setTopBorderColor(colorBorder);

        //Application de bordures pour le corps du tableau
        CellStyle CellStyle = workbook.createCellStyle();
        CellStyle.setBorderTop(styleBorder);
        CellStyle.setBorderBottom(styleBorder);
        CellStyle.setBorderLeft(styleBorder);
        CellStyle.setBorderRight(styleBorder);
        CellStyle.setRightBorderColor(colorBorder);
        CellStyle.setBottomBorderColor(colorBorder);
        CellStyle.setLeftBorderColor(colorBorder);
        CellStyle.setTopBorderColor(colorBorder);

        //Création des headers
        Sheet sheet = workbook.createSheet("Clients");
        Row headerRow = sheet.createRow(0);
        Cell cellTitleNom = headerRow.createCell(0);
        cellTitleNom.setCellValue("Nom");
        cellTitleNom.setCellStyle(headerCellStyle);
        Cell cellTitlePrenom = headerRow.createCell(1);
        cellTitlePrenom.setCellValue("Prenom");
        cellTitlePrenom.setCellStyle(headerCellStyle);
        Cell cellTitleAge = headerRow.createCell(2);
        cellTitleAge.setCellValue("Age");
        cellTitleAge.setCellStyle(headerCellStyle);

        int i=1;
        for(ClientDto client:listes) {
            headerRow = sheet.createRow(i);
            Cell cellNom = headerRow.createCell(0);
            cellNom.setCellStyle(CellStyle);
            cellNom.setCellValue(client.nom);
            Cell cellPrenom = headerRow.createCell(1);
            cellPrenom.setCellStyle(CellStyle);
            cellPrenom.setCellValue(client.prenom);
            Cell cellAge = headerRow.createCell(2);
            cellAge.setCellStyle(CellStyle);
            cellAge.setCellValue(client.dateNaissance.until(LocalDate.now()).getYears() + " ans");
            i++;
        }
        workbook.write(outputStream);
        workbook.close();
    }
}
