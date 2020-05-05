package com.example.demo.service.export;


import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ClientService;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
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
public class ExportClientExcelService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;

    public void exportAll(OutputStream outputStream) throws IOException {
//        List<Client> allClients = clientRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clients");

        Row headerRow = sheet.createRow(0);
        
        // Styles des cellules: 
        CellStyle styleGreen = workbook.createCellStyle();
        styleGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        styleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
        
        CellStyle styleBlue = workbook.createCellStyle();
        styleBlue.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        styleBlue.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
        
        CellStyle styleYellow = workbook.createCellStyle();
        styleBlue.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        styleBlue.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
        
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        styleGreen.setFont(font);
        styleBlue.setFont(font);
        styleYellow.setFont(font);
        
        //LIBELLES
        Cell cellLibelleNom = headerRow.createCell(0);
        Cell cellLibellePrenom = headerRow.createCell(1);
        Cell cellLibelleAge = headerRow.createCell(2);
        cellLibelleNom.setCellValue("Nom");
        cellLibellePrenom.setCellValue("Prénom");
        cellLibelleAge.setCellValue("Age");
        cellLibelleNom.setCellStyle(styleGreen);
        cellLibellePrenom.setCellStyle(styleBlue);
        cellLibelleAge.setCellStyle(styleYellow);

        
        
        
        List<ClientDto> listAllClients = this.clientService.findAllClients();
        
        for (int i =0; i<listAllClients.size(); i++) {
        	
        	ClientDto clientDto = listAllClients.get(i);
        	
            Row newRow = sheet.createRow(i+1);
            
            Cell firstCell = newRow.createCell(0);
            firstCell.setCellStyle(styleGreen);
            firstCell.setCellValue(clientDto.nom);
            
            Cell secondCell =newRow.createCell(1);
            secondCell.setCellValue(clientDto.prenom);
            secondCell.setCellStyle(styleBlue);
            
            Cell thirdCell =newRow.createCell(3);
            thirdCell.setCellValue(clientDto.age);
            thirdCell.setCellStyle(styleYellow);
		}
        

        workbook.write(outputStream);
        workbook.close();
    }

}

