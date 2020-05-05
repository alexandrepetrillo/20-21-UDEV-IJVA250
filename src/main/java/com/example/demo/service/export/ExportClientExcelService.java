package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
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
public class ExportClientExcelService {

    @Autowired
    private ClientRepository clientRepository;

    public void exportAllClientXlsx(OutputStream outputStream) throws IOException {

        List<Client> clients= clientRepository.findAll();
        Client template;


        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clients");

        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < clients.size(); i++){

            //To change a font color to PINK
                //Font font = workbook.createFont();
                //font.setColor(IndexedColors.PINK.getIndex());
            //style.setFont(font);

            //To change a border color to PINK
            CellStyle bStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.PINK.getIndex());
            bStyle.setFont(font);
            bStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.getIndex());

            // Create first HEADER colomn
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Nom");
            headerCell.setCellStyle(bStyle);

            // Create second HEADER colomn
            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("Prenom");
            headerCell.setCellStyle(bStyle);

            // Create third HEADER colomn
            headerCell = headerRow.createCell(2);
            headerCell.setCellValue("Age");
            headerCell.setCellStyle(bStyle);

            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            template = clients.get(i);

            //Create SECOND ROW .. after header row
            Row row = sheet.createRow(i+1);

            // Add NOM in to cell
            Cell cell = row.createCell(0);
            cell.setCellValue(template.getNom());
            cell.setCellStyle(style);

            // Add PRENOM digit in to cell
            cell = row.createCell(1);
            cell.setCellValue(template.getPrenom());
            cell.setCellStyle(style);

            LocalDate d = template.getDateNaissance();

    // Calculate an age by using LOCAL DATE util
            LocalDate today = LocalDate.now();                  //Today's date
            LocalDate birthday = d;                             //Birth date

            int p = Period.between(birthday, today).getYears();

    // Add age digit in to cell
            cell = row.createCell(2);
            cell.setCellValue(String.valueOf(p));
            cell.setCellStyle(style);

        }


        workbook.write(outputStream);
        workbook.close();

    }
}

