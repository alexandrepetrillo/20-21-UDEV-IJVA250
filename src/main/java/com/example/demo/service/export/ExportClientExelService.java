package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class ExportClientExelService {
    @Autowired
    ClientService clientService;

    public void exportAll(OutputStream outputStream) throws IOException {
        List<ClientDto> clients = clientService.findAllClients();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clients");

        CellStyle style = workbook.createCellStyle();
        CellStyle normalStyle = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setColor(HSSFColor.HSSFColorPredefined.LAVENDER.getIndex());
        style.setFont(font);
        style.setBorderTop(BorderStyle.THICK);
        style.setBorderBottom(BorderStyle.THICK);
        style.setBorderLeft(BorderStyle.THICK);
        style.setBorderRight(BorderStyle.THICK);
        style.setBottomBorderColor(IndexedColors.AQUA.getIndex());
        style.setTopBorderColor(IndexedColors.AQUA.getIndex());
        style.setLeftBorderColor(IndexedColors.AQUA.getIndex());
        style.setRightBorderColor(IndexedColors.AQUA.getIndex());

        normalStyle.setBorderTop(BorderStyle.THICK);
        normalStyle.setBorderBottom(BorderStyle.THICK);
        normalStyle.setBorderLeft(BorderStyle.THICK);
        normalStyle.setBorderRight(BorderStyle.THICK);

        normalStyle.setBottomBorderColor(IndexedColors.AQUA.getIndex());
        normalStyle.setTopBorderColor(IndexedColors.AQUA.getIndex());
        normalStyle.setLeftBorderColor(IndexedColors.AQUA.getIndex());
        normalStyle.setRightBorderColor(IndexedColors.AQUA.getIndex());

        Integer ligne = 0;

        Row firstHeaderRow = sheet.createRow(ligne);

        Cell CellulePrenom = firstHeaderRow.createCell(0);
        Cell CelluleNom = firstHeaderRow.createCell(1);
        Cell CelluleAge = firstHeaderRow.createCell(2);

        CellulePrenom.setCellValue("Prenom");
        CelluleNom.setCellValue("Nom");
        CelluleAge.setCellValue("Age");

        CellulePrenom.setCellStyle(style);
        CelluleNom.setCellStyle(style);
        CelluleAge.setCellStyle(style);

        LocalDate today = LocalDate.now();

        for (ClientDto clientDto : clients) {
            ligne = ligne + 1;
            Row headerRow = sheet.createRow(ligne);

            Cell cellPrenom = headerRow.createCell(0);
            Cell cellNom = headerRow.createCell(1);
            Cell cellAge = headerRow.createCell(2);

            cellPrenom.setCellValue(clientDto.prenom);
            cellNom.setCellValue(clientDto.nom);
            cellAge.setCellValue(Period.between(clientDto.dateNaisance, today).getYears() + " ans");

            cellPrenom.setCellStyle(normalStyle);
            cellNom.setCellStyle(normalStyle);
            cellAge.setCellStyle(normalStyle);
        }

        workbook.write(outputStream);
        workbook.close();
    }
}
