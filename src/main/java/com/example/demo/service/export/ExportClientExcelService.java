package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;

@Service
public class ExportClientExcelService {

    @Autowired
    private ClientService clientService;

    private static String[] columns = {"nom", "prenom", "age"};

    public void exportAll(OutputStream outputStream) throws IOException {

        // Create workbook
        Workbook workbook = new XSSFWorkbook();

        // Create sheet
        Sheet sheet = workbook.createSheet("Client");

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

        List<ClientDto> allClients = clientService.findAllClients();

        for (ClientDto client : allClients) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(client.getNom());
            row.createCell(1).setCellValue(client.getPrenom());
            row.createCell(2).setCellValue(LocalDate.now().getYear() - client.getDateNaissance().getYear());
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

