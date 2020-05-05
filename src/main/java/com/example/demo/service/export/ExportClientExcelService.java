package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.dto.LigneFactureDto;
import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ExportClientExcelService {

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

        Integer row = 0;
        Row HeaderRow = sheet.createRow(row);

        Cell pCellPrenom = HeaderRow.createCell(0);
        Cell pCellNom = HeaderRow.createCell(1);
        Cell pCellAge = HeaderRow.createCell(2);

        pCellNom.setCellValue("Nom");
        pCellPrenom.setCellValue("Prenom");
        pCellAge.setCellValue("Age");

        pCellPrenom.setCellStyle(style);
        pCellNom.setCellStyle(style);
        pCellAge.setCellStyle(style);

        LocalDate today = LocalDate.now();
        for (ClientDto clientDto : clients) {
            row = row + 1;
            Row headerRow = sheet.createRow(row);

            Cell cellPrenom = headerRow.createCell(0);
            Cell cellNom = headerRow.createCell(1);
            Cell cellAge = headerRow.createCell(2);

            cellPrenom.setCellValue(clientDto.prenom);
            cellNom.setCellValue(clientDto.nom);
            cellAge.setCellValue(Period.between(clientDto.dateNaissance, today).getYears() + " ans");

            cellPrenom.setCellStyle(normalStyle);
            cellNom.setCellStyle(normalStyle);
            cellAge.setCellStyle(normalStyle);
        }

        workbook.write(outputStream);
        workbook.close();
    }


    public void exportOneWithInvoice(OutputStream OutputStream, Long clientId) throws IOException {

    }
}
