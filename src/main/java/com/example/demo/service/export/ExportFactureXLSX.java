package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;

@Service
public class ExportFactureXLSX {

	@Autowired
    private FactureService factureService;
	
	@Autowired
	private ClientService clientService;
	
    public void exportAll(OutputStream outputStream) throws IOException {
        
    	//init work book
    	Workbook workbook = new XSSFWorkbook();
    	
    	CellStyle style = workbook.createCellStyle(); 
        // Creating Font and settings  
        XSSFFont  font = (XSSFFont) workbook.createFont();  
        font.setFontHeightInPoints((short)12);  
        font.setFontName("Helvetica Neue");  
        font.setBold(false);  
    	style.setFont(font);  
    	
    	CellStyle styleBold = workbook.createCellStyle(); 
    	styleBold.setAlignment(HorizontalAlignment.CENTER);
        // Creating Font and settings  
        XSSFFont  fontBold = (XSSFFont) workbook.createFont();  
        fontBold.setFontHeightInPoints((short)12);  
        fontBold.setFontName("Helvetica Neue");  
        fontBold.setBold(true);  
        styleBold.setFont(fontBold);  
        
    	
    	List<FactureDto> factList = factureService.findAllFactures();
    	//go through clients
    	
    	List<ClientDto> cliList = clientService.findAllClients();
    	for (ClientDto client : cliList) {
			//(client.nom + ";" + client.prenom + ";" + client.age);
    		Sheet sheet = workbook.createSheet(client.nom+ " "+ client.prenom);
    		Row headerRow = sheet.createRow(0);
    		
    		Cell cell1 = headerRow.createCell(0);
        	cell1.setCellValue("Nom");
        	Cell cell11 = headerRow.createCell(1);
        	cell11.setCellValue(client.nom);
        	
        	Row headerRow2 = sheet.createRow(1);
        	Cell cell2 = headerRow2.createCell(0);
        	cell2.setCellValue("Prénom");
        	Cell cell22 = headerRow2.createCell(1);
        	cell22.setCellValue(client.prenom);
        	
        	Row headerRow3 = sheet.createRow(2);
        	Cell cell3 = headerRow3.createCell(0);
        	cell3.setCellValue("Date de naissance");
        	Cell cell33 = headerRow3.createCell(1);
        	cell33.setCellValue(client.age);
        	sheet.autoSizeColumn(0);
        	sheet.autoSizeColumn(1);
        	sheet.autoSizeColumn(2);
        	for (FactureDto factureDto : factList) {
        		if (factureDto.client.id.equals(client.id)) {
        			Sheet sheet2 = workbook.createSheet("Facture n°"+factureDto.id);
            		Row headerRowFact = sheet2.createRow(0);
            		
            		
            		style.setFont(font);  
            		Cell cellF = headerRowFact.createCell(0);
                	cellF.setCellValue("Désignation");
                	cellF.setCellStyle(styleBold);  
                	Cell cellF2 = headerRowFact.createCell(1);
                	cellF2.setCellValue("Quantité");
                	cellF2.setCellStyle(styleBold);  
                	Cell cellF3 = headerRowFact.createCell(2);
                	cellF3.setCellValue("Prix unitaire");
                	cellF3.setCellStyle(styleBold);  
                	
                	int i= 1;
                	
                	List<LigneFactureDto> lignFactList =  factureDto.ligneFactures;
                	for (LigneFactureDto facture : lignFactList) {
                		
                		Row headerRowLigne = sheet2.createRow(i);
                		
                		Cell cellL = headerRowLigne.createCell(0);
                    	cellL.setCellValue(facture.article.libelle);
                    	cellL.setCellStyle(style);  
                    	Cell cellL2 = headerRowLigne.createCell(1);
                    	cellL2.setCellValue(facture.quantite);
                    	cellL2.setCellStyle(style);  
                    	Cell cellL3 = headerRowLigne.createCell(2);
                    	cellL3.setCellValue(facture.article.prix);
                    	cellL3.setCellStyle(style);  
                    	i++;
        			}
                	sheet2.autoSizeColumn(0);
                	sheet2.autoSizeColumn(1);
                	sheet2.autoSizeColumn(2);
        		}
        		ClientDto cliFact = factureDto.client;
        		
        	}
		}
    	
    	workbook.write(outputStream);
    	workbook.close();
    	
    }
	
}
