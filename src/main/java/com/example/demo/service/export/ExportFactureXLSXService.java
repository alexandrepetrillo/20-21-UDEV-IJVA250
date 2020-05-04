package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;

import autre.Facture;

@Service
public class ExportFactureXLSXService {
	
	@Autowired
	FactureService factureService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	ExportClientXLSXService exportClientXLSXService;
	
	
	/**
	 * @param fileOutputStream
	 * @throws IOException
	 */
	public void exportAll(OutputStream fileOutputStream) throws IOException {
		
		List<ClientDto> toutlesClient = clientService.findAllClients();
    	List<FactureDto> touteLesFactures = factureService.findAllFactures();
    	Workbook workbook = new XSSFWorkbook();
    	Sheet sheet;
    	for(ClientDto c : toutlesClient) {
    		
        	sheet = workbook.createSheet(c.getNom()+" "+c.getPrenom());
        	Row headerRow = sheet.createRow(0);
    		Cell cellNomName = headerRow.createCell(0);
    		cellNomName.setCellValue("Nom");
    		Cell cellNomValue = headerRow.createCell(1);
    		cellNomValue.setCellValue(c.getNom());
    		headerRow = sheet.createRow(1);
    		Cell cellPrenomName = headerRow.createCell(0);
    		cellPrenomName.setCellValue("Prenom");
    		Cell cellPrenomValue = headerRow.createCell(1);
    		cellPrenomValue.setCellValue(c.getPrenom());
    		headerRow = sheet.createRow(2);
    		Cell cellDateDeNaissanceName = headerRow.createCell(0);
    		cellDateDeNaissanceName.setCellValue("Date de naissance");
    		Cell cellDateDeNaissanceValue = headerRow.createCell(1);
    		cellDateDeNaissanceValue.setCellValue(c.getDateDeNaissance().toString());

    		for(FactureDto facture:touteLesFactures) {
    			if(c.equals(facture.getClient())) {
    			//j'ajoute c.getId() dans le nommage afain d'eviter un illigal argument exception
    			sheet = workbook.createSheet("Facture n°"+facture.getId());
            	headerRow = sheet.createRow(0);
        		Cell cellDesignationName = headerRow.createCell(0);
        		cellDesignationName.setCellValue("Désignation");
        		Cell cellQuantiteName = headerRow.createCell(1);
        		cellQuantiteName.setCellValue("Quantité");
        		Cell cellPrixName = headerRow.createCell(2);
        		cellPrixName.setCellValue("Prix unitaire");
        		int i=1;
        		for(LigneFactureDto lignes:facture.getLigneFactures()) {
        			headerRow = sheet.createRow(i);
        			Cell cellDesignationValue = headerRow.createCell(0);
        			cellDesignationValue.setCellValue(lignes.getArticle().getLibelle());
            		Cell cellQuantiteValue = headerRow.createCell(1);
            		cellQuantiteValue.setCellValue(lignes.getQuantite());
            		Cell cellPrixValue = headerRow.createCell(2);
            		cellPrixValue.setCellValue(lignes.getArticle().getPrix());
            		i++;
        		}}
    			
    		}	
    	}
    	workbook.write(fileOutputStream);
    	workbook.close();
    	}
    	
    	
       }


